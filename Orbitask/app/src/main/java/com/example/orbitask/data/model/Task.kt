package com.example.orbitask.data.model

import androidx.compose.runtime.Immutable
import java.time.LocalDateTime

@Immutable
data class Task(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.MEDIUM,
    val dueDate: LocalDateTime? = null,
    val isCompleted: Boolean = false,
    val subtasks: List<Subtask> = emptyList(),
    val tags: List<String> = emptyList(),
    val userId: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    enum class Priority {
        HIGH, MEDIUM, LOW
    }
}

data class Subtask(
    val id: String = "",
    val title: String = "",
    val isCompleted: Boolean = false
)