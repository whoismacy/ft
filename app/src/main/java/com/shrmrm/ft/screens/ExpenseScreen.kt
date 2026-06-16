package com.shrmrm.ft.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shrmrm.ft.LocalAppNavigator
import com.shrmrm.ft.components.EmptyState
import com.shrmrm.ft.components.ExpenseDialog
import com.shrmrm.ft.components.ScreenFab
import com.shrmrm.ft.components.SingleExpense
import com.shrmrm.ft.components.TabDestinations
import com.shrmrm.ft.data.viewmodels.FtViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ExpenseScreen(viewModel: FtViewModel) {
    val state by viewModel.ftUiViewState.collectAsStateWithLifecycle()
    val expenses = state.expenses

    var dialogActive by rememberSaveable { mutableStateOf(false) }
    var selectedTabDestination by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        snackbarHost = { SnackbarHost(viewModel.snackBarHost) },
        floatingActionButton = {
            ScreenFab(
                text = "Add expense",
                onClick = { dialogActive = true },
            )
        },
        topBar = {
            TopAppBar(title = {
                Text(
                    if (expenses.isEmpty()) {
                        "NO EXPENSES TODAY"
                    } else if (expenses.size == 1) {
                        "1 EXPENSE TODAY"
                    } else {
                        "${expenses.size} EXPENSES TODAY"
                    },
                    style = MaterialTheme.typography.titleLargeEmphasized,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            })
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PrimaryScrollableTabRow(
                selectedTabIndex = selectedTabDestination,
                scrollState = rememberScrollState(),
                modifier = Modifier.padding(innerPadding),
            ) {
                TabDestinations.entries.forEach { destinations ->
                    selectedTabDestination = destinations.ordinal
                    Tab(
                        selected = destinations.route == navigator.backStack.last(),
                        onClick = { navigator.navigateTo(destinations.route) },
                        text = { Text(destinations.value) },
                    )
                }
            }
            Box(
                contentAlignment = Alignment.Center,
            ) {
                if (expenses.isEmpty()) {
                    EmptyState(
                        "No expenses found!",
                        supportingMessage = "Create new expenses to see them here.",
                    )
                } else {
                    LazyColumn(
                        state = rememberLazyListState(),
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(start = 16.dp, end = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(items = expenses, key = { it.expenseId }) { expense ->
                            SingleExpense(expense, viewModel = viewModel)
                        }
                    }
                }
            }

            if (dialogActive) {
                ExpenseDialog(viewModel = viewModel) { dialogActive = false }
            }
        }
    }
}
