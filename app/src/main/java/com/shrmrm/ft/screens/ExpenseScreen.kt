package com.shrmrm.ft.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.shrmrm.ft.components.ExpenseDialog
import com.shrmrm.ft.components.ScreenFab
import com.shrmrm.ft.components.TabDestinations
import com.shrmrm.ft.data.viewmodels.FtViewModel
import com.shrmrm.ft.navigation.Routes

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpenseScreen(viewModel: FtViewModel) {
    var dialogActive by rememberSaveable { mutableStateOf(false) }
    val tabBackStack = remember { mutableStateListOf<Routes>(Routes.TodaysExpensesRoute) }
    val currentTab = tabBackStack.last()

    Scaffold(
        snackbarHost = { SnackbarHost(viewModel.snackBarHost) },
        floatingActionButton = {
            ScreenFab(
                text = "Add expense",
                onClick = { dialogActive = true },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
        ) {
            PrimaryScrollableTabRow(
                selectedTabIndex =
                    when (currentTab) {
                        is Routes.TodaysExpensesRoute -> {
                            0
                        }

                        is Routes.YesterdayExpensesRoute -> {
                            1
                        }

                        is Routes.PastWeekExpensesRoute -> {
                            2
                        }

                        is Routes.MoreExpensesRoute -> {
                            3
                        }

                        else -> {
                            0
                        }
                    },
                scrollState = rememberScrollState(),
            ) {
                TabDestinations.entries.forEach { destinations ->
                    Tab(
                        selected = currentTab == destinations.route,
                        onClick = {
                            if (currentTab != destinations.route) {
                                tabBackStack.clear()
                                tabBackStack.add(destinations.route)
                            }
                        },
                        text = { Text(destinations.value) },
                    )
                }
            }

            NavDisplay(
                backStack = tabBackStack,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(top = 8.dp),
                entryDecorators =
                    listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator(),
                    ),
                entryProvider =
                    entryProvider {
                        entry<Routes.TodaysExpensesRoute> {
                            TodayExpensesScreen(
                                viewModel = viewModel,
                            )
                        }

                        entry<Routes.YesterdayExpensesRoute> {
                            YesterdayExpensesScreen(
                                viewModel = viewModel,
                            )
                        }

                        entry<Routes.PastWeekExpensesRoute> {
                            PastWeekExpensesScreen(
                                viewModel = viewModel,
                            )
                        }

                        entry<Routes.MoreExpensesRoute> {
                            MoreExpensesScreen(
                                viewModel = viewModel,
                            )
                        }
                    },
            )
        }
        if (dialogActive) {
            ExpenseDialog(viewModel = viewModel) { dialogActive = false }
        }
    }
}
