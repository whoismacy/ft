package com.shrmrm.ft.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.shrmrm.ft.data.domain.TaskState
import com.shrmrm.ft.data.viewmodels.FtIntent
import com.shrmrm.ft.data.viewmodels.FtViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SingleTask(
    task: Task,
    viewModel: FtViewModel,
    modifier: Modifier = Modifier,
) {
    Card(
        border =
            BorderStroke(
                width = 1.dp,
                color =
                    MaterialTheme
                        .colorScheme.outlineVariant
                        .copy(alpha = 0.2f),
            ),
        colors =
            CardDefaults
                .cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation =
            CardDefaults
                .cardElevation(.5.dp),
        modifier =
            modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp,
                    horizontal = 12.dp,
                ).clip(RoundedCornerShape(8.dp)),
    ) {
        Row(
            modifier =
                Modifier.fillMaxWidth().padding(
                    vertical = 8.dp,
                    horizontal = 12.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            RadioButton(onClick = {
                viewModel
                    .handleIntent(
                        FtIntent
                            .CompleteTask(
                                task.id,
                                TaskState.DONE.name,
                            ),
                    )
            }, selected = false)
            Text(
                task.name,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLargeEmphasized,
            )
        }
    }
}
