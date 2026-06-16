package com.shrmrm.ft.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shrmrm.ft.data.viewmodels.FtViewModel
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun YesterdayExpensesScreen(
    viewModel: FtViewModel,
    modifier: Modifier = Modifier,
) {
    val zone = ZoneId.systemDefault()
    val today = LocalDate.now()

    val startOfToday = today.atStartOfDay(zone).toInstant()
    val startOfYesterday = today.minusDays(1).atStartOfDay(zone).toInstant()

    val expenses =
        viewModel
            .getExpenseInRange(startOfYesterday, startOfToday)
            .collectAsStateWithLifecycle(emptyList())
            .value
    val highest = expenses.sortedBy { it.amount }.last()
    val sumIn = expenses.filter { it.amount > 0 }.sumOf { it.amount }
    val sumOut = expenses.filter { it.amount < 0 }.sumOf { it.amount }

    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
    }
}
