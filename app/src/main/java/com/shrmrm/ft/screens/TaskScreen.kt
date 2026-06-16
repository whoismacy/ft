package com.shrmrm.ft.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shrmrm.ft.components.DailyTasks
import com.shrmrm.ft.components.EmptyState
import com.shrmrm.ft.components.ScreenFab
import com.shrmrm.ft.data.viewmodels.FtViewModel
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TaskScreen(viewModel: FtViewModel) {
    val state =
        viewModel
            .ftUiViewState
            .collectAsStateWithLifecycle()
            .value
    val tasksGroupedByDate =
        state
            .tasks
            .groupBy { it.created }
            .toSortedMap(compareBy<Instant> { it }.reversed())
    val allTasks = tasksGroupedByDate.values.toList()

    Scaffold(
        snackbarHost = {
            SnackbarHost(viewModel.snackBarHost)
        },
        floatingActionButton = {
            ScreenFab(onClick = {})
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
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(allTasks) {
                        DailyTasks(it, viewModel)
                    }
                }
            }
        }
    }
}
