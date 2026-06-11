package com.shrmrm.ft.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.shrmrm.ft.data.viewmodels.FtViewModel
import com.shrmrm.ft.screens.ExpenseScreen
import com.shrmrm.ft.screens.TaskScreen

private val animation =
    fadeIn(tween(1000)) togetherWith fadeOut(tween(1000))

@Composable
fun RootNavigation(
    modifier: Modifier = Modifier,
    viewModel: FtViewModel = hiltViewModel(),
) {
    val backStack = rememberNavBackStack(Routes.HomeRoute)
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators =
            listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
        entryProvider =
            entryProvider {
                entry<Routes.HomeRoute> {
                    ExpenseScreen(
                        modifier,
                        viewModel = viewModel,
                    )
                }
                entry<Routes.TasksRoute> {
                    TaskScreen(
                        modifier,
                        viewModel = viewModel,
                    )
                }
            },
        transitionSpec = { animation },
        popTransitionSpec = { animation },
        predictivePopTransitionSpec = { animation },
    )
}
