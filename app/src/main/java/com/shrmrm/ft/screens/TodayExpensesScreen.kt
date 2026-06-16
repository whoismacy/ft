package com.shrmrm.ft.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shrmrm.ft.components.EmptyState
import com.shrmrm.ft.components.SingleExpense
import com.shrmrm.ft.data.viewmodels.FtViewModel
import kotlin.time.Clock

@Composable
fun TodayExpensesScreen(
    viewModel: FtViewModel,
    modifier: Modifier = Modifier,
) {
    val expenses =
        viewModel.ftUiViewState
            .collectAsStateWithLifecycle()
            .value
            .expenses

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (expenses.isEmpty()) {
            EmptyState(
                "No expenses Today!",
                supportingMessage = "Spend some money to see expenses here",
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
}
