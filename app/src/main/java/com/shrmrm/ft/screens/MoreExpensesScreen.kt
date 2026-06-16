package com.shrmrm.ft.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shrmrm.ft.data.viewmodels.FtViewModel
import java.time.LocalDate
import java.time.ZoneId
import kotlin.collections.filter
import kotlin.collections.sortedBy

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MoreExpensesScreen(
    viewModel: FtViewModel,
    modifier: Modifier = Modifier,
) {
    val zone = ZoneId.systemDefault()
    val today = LocalDate.now()

    val startOfSixMonths =
        today
            .minusMonths(6)
            .atStartOfDay(zone)
            .toInstant()
    val startOfTomorrow =
        today
            .plusDays(1)
            .atStartOfDay(zone)
            .toInstant()

    val expenses =
        viewModel
            .getExpenseInRange(startOfSixMonths, startOfTomorrow)
            .collectAsStateWithLifecycle(emptyList())
            .value

    val highest = expenses.sortedBy { it.amount }.last()
    val sumIn = expenses.filter { it.amount > 0 }.sumOf { it.amount }
    val sumOut = expenses.filter { it.amount < 0 }.sumOf { it.amount }

    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            Text("Past Six Months")
            Spacer(Modifier.height(24.dp))
            Text("Total Expenses: ${expenses.size}")
            Text("In: $sumIn")
            Text("Out: $sumOut")
            Text("Highest Spend: ${highest.name} -> ${highest.amount}")
        }
    }
}
