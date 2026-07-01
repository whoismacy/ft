package com.shrmrm.ft.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shrmrm.ft.components.BentoBox
import com.shrmrm.ft.components.EmptyState
import com.shrmrm.ft.data.viewmodels.FtViewModel
import com.shrmrm.ft.utils.convertFromInstant
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun YesterdayExpensesScreen(viewModel: FtViewModel) {
    val zone = ZoneId.systemDefault()
    val today = LocalDate.now()

    val startOfToday = today.atStartOfDay(zone).toInstant()
    val startOfYesterday = today.minusDays(1).atStartOfDay(zone).toInstant()

    val expenses =
        viewModel
            .getExpenseInRange(startOfYesterday, startOfToday)
            .collectAsStateWithLifecycle(emptyList())
            .value

    if (expenses.isEmpty()) {
        EmptyState(message = "No expenses found", supportingMessage = "Zero expenses on ${convertFromInstant(startOfYesterday)}")
    } else {
        BentoBox(title = "Yesterday's Expenses", expenses = expenses)
    }
}
