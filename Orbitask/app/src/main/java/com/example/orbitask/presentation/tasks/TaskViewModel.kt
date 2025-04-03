package com.example.orbitask.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orbitask.data.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: FirebaseRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<TaskUiState>(TaskUiState.Loading)
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.signInAnonymously()
            repository.currentUserId?.let { userId ->
                repository.getTasksRealTime(userId).collect { tasks ->
                    _uiState.value = TaskUiState.Success(tasks)
                }
            } ?: run {
                _uiState.value = TaskUiState.Error("User not authenticated")
            }
            }
        }
    }

    fun toggleTaskCompletion(taskId: String) {
        viewModelScope.launch {
            _tasks.value.find { it.id == taskId }?.let { task ->
                repository.updateTask(task.copy(isCompleted = !task.isCompleted))
            }
        }
    }

    fun addTask(title: String, priority: Task.Priority) {
        viewModelScope.launch {
            repository.currentUserId?.let { userId ->
                val task = Task(
                    title = title,
                    priority = priority,
                    userId = userId
                )
                repository.addTask(task)
            }
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            repository.deleteTask(taskId)
        }
    }
}