package com.codemave.mobilecomputing.ui.home.categoryTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codemave.mobilecomputing.Graph
import com.codemave.mobilecomputing.data.entity.Task
import com.codemave.mobilecomputing.data.repository.TaskRepository
import com.codemave.mobilecomputing.data.room.TaskToCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class CategoryTaskViewModel(
    private val categoryId: Long,
    private val taskRepository: TaskRepository = Graph.taskRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CategoryTaskViewState())

    val state: StateFlow<CategoryTaskViewState>
        get() = _state

    init {
        viewModelScope.launch {
            taskRepository.tasksInCategory(categoryId).collect { list ->
                _state.value = CategoryTaskViewState(
                    tasks = list
                )
            }
        }
    }
    suspend fun deleteTask(task: Task): Int {
        return taskRepository.deleteTask(task)
    }
}

data class CategoryTaskViewState(
    val tasks: List<TaskToCategory> = emptyList()
)