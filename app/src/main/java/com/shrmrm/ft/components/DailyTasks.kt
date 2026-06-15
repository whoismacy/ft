package com.shrmrm.ft.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shrmrm.ft.data.domain.Task
import com.shrmrm.ft.data.viewmodels.FtViewModel

@Composable
fun DailyTasks(
    tasks: List<Task>,
    viewModel: FtViewModel,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text("${tasks[0].created}")
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(tasks) {
                SingleTask(it, viewModel)
            }
        }
    }
}

@Composable
fun SingleTask(
    task: Task,
    viewModel: FtViewModel,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        colors =
            CardDefaults
                .cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation =
            CardDefaults
                .cardElevation(1.dp),
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .clip(RoundedCornerShape(8.dp)),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            RadioButton(onClick = {}, selected = false)
            Text(
                task.name,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMediumEmphasized,
            )
        }
    }
}
