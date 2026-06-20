package com.shrmrm.ft.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.shrmrm.ft.data.events.EventManager
import com.shrmrm.ft.data.viewmodels.FtIntent
import com.shrmrm.ft.data.viewmodels.FtViewModel

@Composable
fun TaskDialog(
    viewModel: FtViewModel,
    onDismissRequest: () -> Unit,
) {
    var taskValue by rememberSaveable { mutableStateOf("") }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createTask() {
        if (taskValue.isEmpty()) {
            EventManager
                .triggerEvent(
                    EventManager
                        .AppEvent
                        .ShowSnackbar("Error: Task cannot be empty!"),
                )
        } else {
            viewModel
                .handleIntent(FtIntent.CreateTask(name = taskValue))
            onDismissRequest()
        }
    }
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            shape = MaterialTheme.shapes.large,
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    "Create New Task",
                    style = MaterialTheme.typography.headlineMediumEmphasized,
                )
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value = taskValue,
                    onValueChange = { text: String -> taskValue = text },
                    label = { Text("New Task") },
                    placeholder = { Text("Input new Task here") },
                    minLines = 1,
                    maxLines = 3,
                    singleLine = true,
                )
                ElevatedButton(onClick = { createTask() }) {
                    Text("Create task")
                }
            }
        }
    }
}
