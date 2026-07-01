package com.shrmrm.ft.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
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
import com.shrmrm.ft.utils.instantStartOfTheDay
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun SingleTask(
    task: Task,
    viewModel: FtViewModel,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors =
            CardDefaults
                .cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier =
            modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp,
                ),
        border =
            BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
            ),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                task.name,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
            )
            DayEntries(task, viewModel)
        }
    }
}

@Composable
fun DayEntries(
    task: Task,
    viewModel: FtViewModel,
) {
    val zone = ZoneId.systemDefault()
    val today = LocalDate.now(zone)

    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
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
@Composable
fun DayEntry(
    date: Instant,
    task: Task,
    viewModel: FtViewModel,
) {
    val context = LocalContext.current
    val normalizedDate = instantStartOfTheDay(date)
    val taskLog by
        remember(
            task.id,
            date,
        ) { viewModel.getTaskLog(task.id, normalizedDate) }
            .collectAsStateWithLifecycle(null)

    val zone = ZoneId.systemDefault()
    val cellDate = date.atZone(zone).toLocalDate()
    val taskCreatedDate = task.created.atZone(zone).toLocalDate()
    val today = LocalDate.now(zone)
    val isBeforeCreated = cellDate.isBefore(taskCreatedDate)
    val isToday = cellDate == today

    val (containerColor, contentColor, icon) =
        when {
            isBeforeCreated -> {
                Triple(
                    MaterialTheme.colorScheme.surfaceVariant,
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    R.drawable.baseline_block_24,
                )
            }

            taskLog?.status == TaskState.DONE.status -> {
                Triple(
                    MaterialTheme.colorScheme.tertiaryContainer,
                    MaterialTheme.colorScheme.onTertiaryContainer,
                    TaskState.DONE.icon,
                )
            }

            taskLog?.status == TaskState.HOLD.status -> {
                Triple(
                    MaterialTheme.colorScheme.secondaryContainer,
                    MaterialTheme.colorScheme.onSecondaryContainer,
                    TaskState.HOLD.icon,
                )
            }

            taskLog?.status == TaskState.FAILED.status -> {
                Triple(
                    MaterialTheme.colorScheme.errorContainer,
                    MaterialTheme.colorScheme.onErrorContainer,
                    TaskState.FAILED.icon,
                )
            }

            else -> {
                Triple(
                    MaterialTheme.colorScheme.surfaceVariant,
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    if (isToday) R.drawable.baseline_question_mark_24 else R.drawable.baseline_event_busy_24,
                )
            }
        }

    if (!isToday) {
        StatusSquare(
            icon = icon,
            contentColor = contentColor,
            containerColor = containerColor,
            onClick = {
                Toast
                    .makeText(
                        context,
                        "Error: Only current day modification allowed",
                        Toast.LENGTH_SHORT,
                    ).show()
            },
        )
    } else {
        StatusSquare(
            icon = icon,
            contentColor = contentColor,
            containerColor = containerColor,
            onTap = {
                viewModel.handleIntent(
                    FtIntent.CompleteTask(TaskLog(id = task.id, status = TaskState.DONE.status, logDate = normalizedDate)),
                )
            },
            onDoubleTap = {
                viewModel.handleIntent(
                    FtIntent.CompleteTask(TaskLog(id = task.id, status = TaskState.HOLD.status, logDate = normalizedDate)),
                )
            },
            onLongPress = {
                viewModel.handleIntent(
                    FtIntent.CompleteTask(TaskLog(id = task.id, status = TaskState.FAILED.status, logDate = normalizedDate)),
                )
            },
        )
    }
}

@Composable
fun StatusSquare(
    icon: Int,
    onTap: (() -> Unit)? = null,
    onDoubleTap: (() -> Unit)? = null,
    onLongPress: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    containerColor: Color,
    contentColor: Color,
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
    Box(
        modifier =
            Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(containerColor)
                .then(interactionModifier),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = contentColor,
        )
    }
}
