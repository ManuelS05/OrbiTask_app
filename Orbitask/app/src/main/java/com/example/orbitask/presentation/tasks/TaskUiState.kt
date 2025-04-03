package com.example.orbitask.presentation.tasks

import com.example.orbitask.data.model.Task

sealed class TaskUiState {
    data class Success(val tasks: List<Task>) : TaskUiState()
    data class Error(val message: String) : TaskUiState()
    object Loading : TaskUiState()
}