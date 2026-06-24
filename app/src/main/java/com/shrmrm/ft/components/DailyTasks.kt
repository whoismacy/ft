package com.shrmrm.ft.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shrmrm.ft.R
import com.shrmrm.ft.data.domain.Task
import com.shrmrm.ft.data.domain.TaskLog
import com.shrmrm.ft.data.domain.TaskState
import com.shrmrm.ft.data.viewmodels.FtIntent
import com.shrmrm.ft.data.viewmodels.FtViewModel
import com.shrmrm.ft.utils.convertFromInstant
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/*
I'll use the Habit Card Design similar
to the one used in Habits Loop app.
*/

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SingleTask(
    task: Task,
    viewModel: FtViewModel,
    modifier: Modifier = Modifier,
) {
    Card(
        colors =
            CardDefaults
                .cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation =
            CardDefaults
                .cardElevation(.5.dp),
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 4.dp,
                    ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                task.name,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
            )
            DayEntries(task, viewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayEntries(
    task: Task,
    viewModel: FtViewModel,
) {
    val zone = ZoneId.systemDefault()
    val today = LocalDate.now(zone)

    Row(
        modifier =
            Modifier
                .fillMaxWidth(0.6f)
                .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        for (i in 0..4) {
            val day =
                today
                    .minusDays(i.toLong())
                    .atStartOfDay(zone)
                    .toInstant()
            DayEntry(
                date = day,
                task = task,
                viewModel,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayEntry(
    date: Instant,
    task: Task,
    viewModel: FtViewModel,
) {
    val zone = ZoneId.systemDefault()
    val cellDate = date.atZone(zone).toLocalDate()
    val taskDate = task.created.atZone(zone).toLocalDate()
    val today = LocalDate.now(zone)

    val iconSize = 16.dp

    val isLoading =
        viewModel.ftUiViewState
            .collectAsStateWithLifecycle()
            .value.isLoading

    val taskLog =
        viewModel
            .getTaskLog(task.id)
            .collectAsStateWithLifecycle(null)
            .value

    val onTap = {
        Log.e("DAILYTASKS", "ON TAP METHOD RECEIVED for ${task.id}")
        viewModel
            .handleIntent(
                FtIntent
                    .CompleteTask(
                        TaskLog(
                            id = task.id,
                            status = TaskState.DONE.status,
                            logDate = Instant.now(),
                        ),
                    ),
            )
    }
    val onDoubleTap = {
        Log.e("DAILYTASKS", "ON DOUBLE TAP METHOD RECEIVED for ${task.id}")
        viewModel
            .handleIntent(
                FtIntent
                    .CompleteTask(
                        TaskLog(
                            id = task.id,
                            status = TaskState.HOLD.status,
                            logDate = Instant.now(),
                        ),
                    ),
            )
    }
    val onLongPress = {
        Log.e("DAILYTASKS", "ON LONG PRESS METHOD RECEIVED for ${task.id}")
        viewModel
            .handleIntent(
                FtIntent
                    .CompleteTask(
                        TaskLog(
                            id = task.id,
                            status = TaskState.FAILED.status,
                            logDate = Instant.now(),
                        ),
                    ),
            )
    }

    if (isLoading) {
        LoadingIndicator(modifier = Modifier.size(iconSize))
    } else {
        when {
            cellDate == today -> {
                Log.e("DAILY TASKS SCREEN When 1", "${taskLog?.status}")
                when (taskLog?.status) {
                    TaskState.DONE.status -> {
                        DisplayIcon(
                            painter = TaskState.DONE.icon,
                            onTap = onTap,
                            onDoubleTap = onDoubleTap,
                            onLongPress = onLongPress,
                        )
                    }

                    TaskState.HOLD.status -> {
                        DisplayIcon(
                            painter = TaskState.HOLD.icon,
                            onTap = onTap,
                            onDoubleTap = onDoubleTap,
                            onLongPress = onLongPress,
                        )
                    }

                    TaskState.FAILED.status -> {
                        DisplayIcon(
                            painter = TaskState.FAILED.icon,
                            onTap = onTap,
                            onDoubleTap = onDoubleTap,
                            onLongPress = onLongPress,
                        )
                    }

                    else -> {
                        DisplayIcon(
                            painter = R.drawable.baseline_question_mark_24,
                            onTap = onTap,
                            onDoubleTap = onDoubleTap,
                            onLongPress = onLongPress,
                        )
                    }
                }
            }

            cellDate.isBefore(taskDate) -> {
                DisplayIcon(
                    painter = R.drawable.baseline_dangerous_24,
                    onClick = {
                        viewModel
                            .triggerEvent(
                                "Error⚠️: Task did not exist on" +
                                    " ${convertFromInstant(date)}",
                            )
                    },
                )
            }

            else -> {
                val errorMessage = "Error⚠️: Cannot modify already passed task."
                when (taskLog?.status) {
                    TaskState.DONE.status -> {
                        DisplayIcon(
                            painter = TaskState.DONE.icon,
                            onClick = {
                                viewModel
                                    .triggerEvent(errorMessage)
                            },
                        )
                    }

                    TaskState.HOLD.status -> {
                        DisplayIcon(
                            painter = TaskState.HOLD.icon,
                            onClick = {
                                viewModel
                                    .triggerEvent(errorMessage)
                            },
                        )
                    }

                    TaskState.FAILED.status -> {
                        DisplayIcon(
                            painter = TaskState.FAILED.icon,
                            onClick = {
                                viewModel
                                    .triggerEvent(errorMessage)
                            },
                        )
                    }

                    else -> {
                        DisplayIcon(
                            painter = R.drawable.baseline_edit_24,
                            onClick = {
                                viewModel
                                    .triggerEvent(errorMessage)
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayIcon(
    painter: Int,
    onTap: (() -> Unit)? = null,
    onDoubleTap: (() -> Unit)? = null,
    onLongPress: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    contentDescription: String? = null,
) {
    val currentOnTap by rememberUpdatedState(onTap)
    val currentOnDoubleTap by rememberUpdatedState(onDoubleTap)
    val currentOnLongPress by rememberUpdatedState(onLongPress)
    val usePointerInput =
        onTap != null ||
            onDoubleTap != null ||
            onLongPress != null
    val interactionModifier =
        when {
            usePointerInput -> {
                Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onTap =
                            if (onTap != null) {
                                { _ -> currentOnTap?.invoke() }
                            } else {
                                null
                            },
                        onDoubleTap =
                            if (onDoubleTap != null) {
                                { _ -> currentOnDoubleTap?.invoke() }
                            } else {
                                null
                            },
                        onLongPress =
                            if (onLongPress != null) {
                                { _ -> currentOnLongPress?.invoke() }
                            } else {
                                null
                            },
                    )
                }
            }

            onClick != null -> {
                Modifier.clickable(onClick = onClick)
            }

            else -> {
                Modifier
            }
        }
    Icon(
        painter = painterResource(painter),
        contentDescription = contentDescription,
        modifier =
            interactionModifier.then(
                Modifier
                    .size(24.dp)
                    .padding(8.dp),
            ),
        tint =
            if (onClick != null) {
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)
            } else {
                MaterialTheme.colorScheme.secondary
            },
    )
}
