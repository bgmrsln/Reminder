package com.codemave.mobilecomputing.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codemave.mobilecomputing.Graph
import com.codemave.mobilecomputing.data.entity.Category
import com.codemave.mobilecomputing.data.entity.Task
import com.codemave.mobilecomputing.data.repository.CategoryRepository
import com.codemave.mobilecomputing.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(
    private val taskRepository: TaskRepository = Graph.taskRepository,
    private val categoryRepository: CategoryRepository = Graph.categoryRepository
): ViewModel() {
    private val _state = MutableStateFlow(TaskViewState())

    val state: StateFlow<TaskViewState>
        get() = _state

    suspend fun saveTask(task: Task): Long {
        return taskRepository.addTask(task)
    }

    init {
        viewModelScope.launch {
            categoryRepository.categories().collect { categories ->
                _state.value = TaskViewState(categories)
            }
        }
    }
}

data class TaskViewState(
    val categories: List<Category> = emptyList()
)