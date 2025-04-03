package com.example.orbitask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.orbitask.presentation.tasks.TaskListScreen
import com.example.orbitask.ui.theme.OrbitaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrbitaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                var showDialog by remember { mutableStateOf(false) }
                
                if (showDialog) {
                    AddTaskDialog(
                        onDismiss = { showDialog = false },
                        onConfirm = { title, priority ->
                            viewModel.addTask(title, priority)
                        }
                    )
                }

                TaskListScreen(
                    onTaskClick = { taskId ->
                        // Handle task click if needed
                    },
                    onAddTask = { showDialog = true }
                )
                }
            }
        }
    }
}
