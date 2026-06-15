package com.shrmrm.ft.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shrmrm.ft.components.EmptyState
import com.shrmrm.ft.components.ScreenFab
import com.shrmrm.ft.components.SingleExpense
import com.shrmrm.ft.data.viewmodels.FtViewModel

@Composable
fun ExpenseScreen(viewModel: FtViewModel) {
    val state = viewModel.ftUiViewState.collectAsStateWithLifecycle().value
    val expenses = state.expenses
    Scaffold(
        floatingActionButton = {
            ScreenFab()
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            if (expenses.isEmpty()) {
                EmptyState("No expenses found!")
            } else {
                Column {
                    LazyColumn {
                        items(expenses) { expense ->
                            SingleExpense(expense)
                        }
                    }
                }
            }
        }
    }
}
