package com.shrmrm.ft.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shrmrm.ft.components.BentoBox
import com.shrmrm.ft.components.EmptyState
import com.shrmrm.ft.data.viewmodels.FtViewModel
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MoreExpensesScreen(viewModel: FtViewModel) {
    val zone = ZoneId.systemDefault()
    val today = LocalDate.now()

    val startOfTomorrow =
        today
            .plusDays(1)
            .atStartOfDay(zone)
            .toInstant()
    val startOfThreeMonths =
        today
            .minusMonths(3)
            .atStartOfDay(zone)
            .toInstant()
    val startOfSixMonths =
        today
            .minusMonths(6)
            .atStartOfDay(zone)
            .toInstant()
    val startOfOneYear =
        today
            .minusMonths(12)
            .atStartOfDay(zone)
            .toInstant()

    val expenses3 =
        viewModel
            .getExpenseInRange(startOfThreeMonths, startOfTomorrow)
            .collectAsStateWithLifecycle(emptyList())
            .value

    val expenses6 =
        viewModel
            .getExpenseInRange(startOfSixMonths, startOfTomorrow)
            .collectAsStateWithLifecycle(emptyList())
            .value

    val expenses12 =
        viewModel
            .getExpenseInRange(startOfOneYear, startOfTomorrow)
            .collectAsStateWithLifecycle(emptyList())
            .value

    if (expenses3.isEmpty() ||
        expenses6.isEmpty() ||
        expenses12.isEmpty()
    ) {
        EmptyState(message = "No expenses found")
    } else {
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            item {
                BentoBox(
                    title = "Past 3 Month's Expenses",
                    expenses = expenses3,
                )
            }
            item {
                BentoBox(
                    title = "Past 6 Month's Expenses",
                    expenses = expenses6,
                )
            }
            item {
                BentoBox(
                    title = "Past Year Expenses",
                    expenses = expenses12,
                )
            }
        }
    }
}
