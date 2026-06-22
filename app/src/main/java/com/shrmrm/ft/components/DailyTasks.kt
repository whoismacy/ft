package com.shrmrm.ft.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shrmrm.ft.R
import com.shrmrm.ft.data.domain.Task
import com.shrmrm.ft.data.events.EventManager
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
            DayEntries(task)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayEntries(task: Task) {
    val zone = ZoneId.systemDefault()
    val today = LocalDate.now(zone)

    Row(
        modifier =
            Modifier
                .fillMaxWidth(0.5f)
                .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        for (i in 0..<5) {
            val day =
                today
                    .minusDays(i.toLong())
                    .atStartOfDay(zone)
                    .toInstant()
            DayEntry(
                date = day,
                task = task,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayEntry(
    date: Instant,
    task: Task,
) {
    val iconSize = 16.dp

    Box(
        contentAlignment = Alignment.Center,
    ) {
        when {
            convertFromInstant(date) == convertFromInstant(task.created) -> {
                Icon(
                    painter = painterResource(R.drawable.baseline_question_mark_24),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .size(iconSize)
                            .clickable(onClick = {
                                EventManager
                                    .triggerEvent(
                                        EventManager
                                            .AppEvent
                                            .ShowSnackbar("Clicked on date ${convertFromInstant(date)}"),
                                    )
                            }),
                )
            }

            task.created > date -> {
                Icon(
                    painter = painterResource(R.drawable.baseline_edit_24),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .size(iconSize)
                            .clickable(onClick = {
                                EventManager
                                    .triggerEvent(
                                        EventManager
                                            .AppEvent
                                            .ShowSnackbar("Clicked on date ${convertFromInstant(date)}"),
                                    )
                            }),
                    tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
                )
            }

            else -> {
                Icon(
                    painter = painterResource(R.drawable.outline_hourglass_empty_24),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .size(iconSize)
                            .clickable(onClick = {
                                EventManager
                                    .triggerEvent(
                                        EventManager
                                            .AppEvent
                                            .ShowSnackbar("Clicked on date ${convertFromInstant(date)}"),
                                    )
                            }),
                    tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
                )
            }
        }
    }
}
