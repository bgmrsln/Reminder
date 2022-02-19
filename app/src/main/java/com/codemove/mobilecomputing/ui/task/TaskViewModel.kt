package com.codemave.mobilecomputing.ui.task

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.ui.unit.Constraints
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.from
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.codemave.mobilecomputing.Graph
import com.codemave.mobilecomputing.R
import com.codemave.mobilecomputing.data.entity.Category
import com.codemave.mobilecomputing.data.entity.Task
import com.codemave.mobilecomputing.data.repository.CategoryRepository
import com.codemave.mobilecomputing.data.repository.TaskRepository
import com.codemave.mobilecomputing.ui.home.categoryTask.toDateString
import com.codemove.mobilecomputing.util.NotificationWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class TaskViewModel(
    private val taskRepository: TaskRepository = Graph.taskRepository,
    private val categoryRepository: CategoryRepository = Graph.categoryRepository,
    private val context: Context= Graph.appContext
): ViewModel() {
    private val _state = MutableStateFlow(TaskViewState())

    val state: StateFlow<TaskViewState>
        get() = _state

    suspend fun saveTask(task: Task): Long {
        if(task.notificationWanted){
            newReminderNotification(task)
            setOneTimeNotification(task)
        }
        if(task.earlyNotification){
            setOneTimeEarlyNotification(task)
        }

        return taskRepository.addTask(task)
    }

    init {
        createNotificationChannel(context= Graph.appContext   )

        viewModelScope.launch {
            categoryRepository.categories().collect { categories ->
                _state.value = TaskViewState(categories)
            }
        }
    }
}
private fun reminderTimeNotification(task: Task){
    val notificationId = 3
    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("You have a reminder")
        .setContentText(task.taskTitle)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    with(NotificationManagerCompat.from(Graph.appContext)) {
        notify(notificationId, builder.build())
    }

}
private fun earlyReminderTimeNotification(task: Task){
    val notificationId = 4
    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("You have a task due in 5 minutes")
        .setContentText(task.taskTitle)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    with(NotificationManagerCompat.from(Graph.appContext)) {
        notify(notificationId, builder.build())
    }

}
private fun setOneTimeNotification(task: Task){
    val workManager= WorkManager.getInstance(Graph.appContext)
    val constraints= androidx.work.Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()
    val data= Data.Builder()
    data.putLong("id", task.taskId)


    //can i make it wait until the time comes?? maybe with a time parameter to this function
    val notificationWorker= OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(task.reminderTime-Date().time, TimeUnit.MILLISECONDS)
        .setConstraints(constraints)
        .setInputData(data.build())
        .build()

    workManager.enqueue(notificationWorker)

    //Monitoring for state of work
    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever { workInfo ->
            if(workInfo.state == WorkInfo.State.SUCCEEDED) {
               reminderTimeNotification(task)
            }
            else{

                //createSuccessNotification(task)
            }
        }
}
private fun setOneTimeEarlyNotification(task: Task){
    val workManager= WorkManager.getInstance(Graph.appContext)
    val constraints= androidx.work.Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()
    val data= Data.Builder()
    data.putLong("id", task.taskId)


    //can i make it wait until the time comes?? maybe with a time parameter to this function
    val notificationWorker= OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(task.reminderTime-Date().time- 300000, TimeUnit.MILLISECONDS)
        .setConstraints(constraints)
        .setInputData(data.build())
        .build()

    workManager.enqueue(notificationWorker)

    //Monitoring for state of work
    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever { workInfo ->
            if(workInfo.state == WorkInfo.State.SUCCEEDED) {
                earlyReminderTimeNotification(task)
            }
            else{


            }
        }
}
private fun createSuccessNotification(task:Task){
    val notificationId =1
    val builder= NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle(task.taskTitle)
        .setContentText("didnt work ${task.reminderTime- Date().time}")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    with(from(Graph.appContext)){
        //notification id is unique for each notification that you define
        notify(notificationId, builder.build())
    }

}
private fun newReminderNotification(task: Task){
    val notificationId= 4
    val builder= NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("New reminder")
        .setContentText("Job is due to: ${task.reminderTime.toDateString()}\n ${task.taskTitle}")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setStyle(NotificationCompat.BigTextStyle().bigText("Job is due to: ${task.reminderTime.toDateString()}\n ${task.taskTitle}"))
    with(from(Graph.appContext)){
        //notification id is unique for each notification that you define
        notify(notificationId, builder.build())

    }

}
private fun createErrorNotification(){
    val notificationId = 2
    val builder= NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("h ey!")
        .setContentText("You have a reminder error ")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    with(from(Graph.appContext)){
        //notification id is unique for each notification that you define
        notify(notificationId, builder.build())
    }

}

private fun createNotificationChannel(context: Context){
    //the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val name= "NotificationChannelName"
        val descriptionText= "NotificationChannelDescriptionText"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel= NotificationChannel("CHANNEL_ID", name, importance).apply{
            description= descriptionText
        }
        //register the channel with the system
        val notificationManager: NotificationManager= context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)



    }


}



data class TaskViewState(
    val categories: List<Category> = emptyList()
)