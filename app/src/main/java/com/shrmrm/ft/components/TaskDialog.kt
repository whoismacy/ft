package com.shrmrm.ft.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.shrmrm.ft.R
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

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(0.9f).wrapContentHeight(),
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
        ) {
            Column(
                modifier =
                    Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(18.dp),
            ) {
                Text(
                    "New Task",
                    style = MaterialTheme.typography.headlineSmall,
                )
                OutlinedTextField(
                    value = taskValue,
                    onValueChange = { text: String -> taskValue = text },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    label = { Text("Task description") },
                    minLines = 1,
                    maxLines = 3,
                    keyboardOptions =
                        KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            autoCorrectEnabled = true,
                            keyboardType = KeyboardType.Text,
                        ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.outline_description_24),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                        )
                    },
                    trailingIcon = {
                        if (taskValue.isNotEmpty()) {
                            IconButton(onClick = { taskValue = "" }) {
                                Icon(
                                    painter = painterResource(R.drawable.outline_close_24),
                                    contentDescription = "Clear",
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            }
                        }
                    },
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.weight(1f),
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = { createTask() },
                        modifier = Modifier.weight(1.5f),
                    ) {
                        Text("Create task")
                    }
                }
            }
        }
    }
}
