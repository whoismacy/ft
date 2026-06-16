package com.shrmrm.ft.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shrmrm.ft.components.BentoBox
import com.shrmrm.ft.components.EmptyState
import com.shrmrm.ft.data.viewmodels.FtViewModel
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PastWeekExpensesScreen(viewModel: FtViewModel) {
    val zone = ZoneId.systemDefault()
    val today = LocalDate.now()

    val startOfTomorrow =
        today
            .plusDays(1)
            .atStartOfDay(zone)
            .toInstant()
    val startOfPreviousWeek =
        today
            .minusWeeks(1)
            .atStartOfDay(zone)
            .toInstant()

    val expenses =
        viewModel
            .getExpenseInRange(startOfPreviousWeek, startOfTomorrow)
            .collectAsStateWithLifecycle(emptyList())
            .value

    if (expenses.isEmpty()) {
        EmptyState(message = "No expenses found")
    } else {
        BentoBox(title = "Yesterday's Expenses", expenses = expenses)
    }
}
