package com.shrmrm.ft.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shrmrm.ft.components.EmptyState
import com.shrmrm.ft.components.ScreenFab
import com.shrmrm.ft.components.SingleTask
import com.shrmrm.ft.components.TaskDialog
import com.shrmrm.ft.data.viewmodels.FtViewModel
import com.shrmrm.ft.utils.convertFromInstant
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TaskScreen(viewModel: FtViewModel) {
    val state =
        viewModel
            .ftUiViewState
            .collectAsStateWithLifecycle()
            .value
    val tasks = state.tasks
    var isFabActive by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(viewModel.snackBarHost)
        },
        floatingActionButton = {
            ScreenFab(
                text = "Add Task",
                onClick = { isFabActive = true },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            if (state.tasks.isEmpty()) {
                EmptyState(
                    message = "No Tasks found!",
                    supportingMessage = "Create new Tasks to see them here",
                )
            } else {
                val zone = ZoneId.systemDefault()
                val today = LocalDate.now().atStartOfDay(zone)
                Column(
                    verticalArrangement =
                        Arrangement.spacedBy(24.dp),
                ) {
                    Spacer(Modifier.height(24.dp))
                    Text(
                        convertFromInstant(today.toInstant()) +
                            " - ${convertFromInstant(today.minusDays(4).toInstant())}",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style =
                            MaterialTheme
                                .typography.displaySmall
                                .copy(fontWeight = FontWeight.ExtraBold),
                    )

                    LazyColumn(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                    ) {
                        items(tasks) { task ->
                            SingleTask(task, viewModel)
                        }
                    }
                }
            }
        }

        if (isFabActive) {
            TaskDialog(viewModel = viewModel) { isFabActive = false }
        }
    }
}
