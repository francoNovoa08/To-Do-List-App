package com.example.todolistapp.uiScreens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistapp.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListScreen() {
    val tasksList = remember { mutableStateListOf<String>() }
    var task by remember { mutableStateOf("") }
    var isSheetOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(tasksList, key = {it}) { task ->
                BuildTask(task = task, onTaskDeleted = { taskToDelete ->
                    tasksList.remove(taskToDelete)
                })
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(
                    topStart = 50.dp,
                    topEnd = 50.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                ))
                .background(LightBlue)
                .height(200.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.height(90.dp))
            Button(onClick = {
                isSheetOpen = true
            },
                colors = ButtonDefaults.buttonColors(
                    contentColor = LightBlue,
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add task",
                    tint = LightBlue,
                    modifier = Modifier.requiredSize(55.dp)
                )
            }

            if (isSheetOpen) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        isSheetOpen = false
                    },
                    containerColor = LightBlue
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(LightBlue)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(40.dp))
                        TextField(
                            value = task,
                            onValueChange = { text ->
                                task = text
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = LightBlue,
                                unfocusedContainerColor = LightBlue,
                                unfocusedIndicatorColor = Color.White,
                                focusedIndicatorColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                        )

                        LaunchedEffect(Unit) {
                            focusRequester.requestFocus()
                        }

                        Spacer(modifier = Modifier.height(40.dp ))
                        Button(onClick = {
                            if (task.isNotBlank()) {
                                if (!tasksList.contains(task)) {
                                    tasksList.add(task)
                                    isSheetOpen = false
                                    task = ""
                                } else {
                                    Toast.makeText(context, "Task already exists", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Save",
                                color = Color.Black
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Add new task",
                fontSize = 40.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun BuildTask(task: String, onTaskDeleted: (String) -> Unit) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(horizontal = 48.dp, vertical = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color.Black.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = task,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.Start)
                .padding(6.dp)
                .weight(4f),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Button(onClick = {
            onTaskDeleted(task)
        },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete task",
                modifier = Modifier.requiredSize(35.dp)
            )
        }
    }
}


