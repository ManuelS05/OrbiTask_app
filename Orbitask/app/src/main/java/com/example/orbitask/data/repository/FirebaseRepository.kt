package com.example.orbitask.data.repository

import com.example.orbitask.data.model.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    val currentUserId: String?
        get() = auth.currentUser?.uid

    suspend fun signInAnonymously() {
        auth.signInAnonymously().await()
    }
    private val tasksCollection = firestore.collection("tasks")

    fun getTasksRealTime(userId: String): Flow<List<Task>> = callbackFlow {
        val listener = tasksCollection
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val tasks = snapshot?.toObjects(Task::class.java) ?: emptyList()
                trySend(tasks)
            }
        
        awaitClose { listener.remove() }
    }

    suspend fun addTask(task: Task) {
        if (task.userId.isBlank()) {
            throw IllegalArgumentException("Task must have a userId")
        }
        tasksCollection.document(task.id).set(task).await()
    }

    suspend fun updateTask(task: Task) {
        tasksCollection.document(task.id).set(task).await()
    }

    suspend fun deleteTask(taskId: String) {
        tasksCollection.document(taskId).delete().await()
    }
}