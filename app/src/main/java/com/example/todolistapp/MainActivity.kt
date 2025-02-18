package com.example.todolistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.example.todolistapp.ui.theme.ToDoListAppTheme
import com.example.todolistapp.uiScreens.ToDoListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoListAppTheme {
                WindowCompat.setDecorFitsSystemWindows(window, false)
                ToDoListScreen()
            }
        }
    }
}
