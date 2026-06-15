package com.shrmrm.ft.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.shrmrm.ft.components.NavigationComponents
import com.shrmrm.ft.data.viewmodels.FtViewModel
import com.shrmrm.ft.screens.ExpenseScreen
import com.shrmrm.ft.screens.TaskScreen

private val animation =
    fadeIn(tween(1000)) togetherWith fadeOut(tween(1000))

@Composable
fun RootNavigation(viewModel: FtViewModel = hiltViewModel()) {
    val backStack = rememberNavBackStack(Routes.HomeRoute)

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            NavigationComponents.entries.forEach {
                item(
                    icon = {
                        Icon(
                            painter = painterResource(it.icon),
                            contentDescription = "An icon of ${it.label} navigation.",
                        )
                    },
                    label = { Text(it.label) },
                    selected = true,
                    onClick = {},
                )
            }
        },
    ) {
        NavDisplay(
            modifier = Modifier,
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
                            viewModel = viewModel,
                        )
                    }
                    entry<Routes.TasksRoute> {
                        TaskScreen(
                            viewModel = viewModel,
                        )
                    }
                },
            transitionSpec = { animation },
            popTransitionSpec = { animation },
            predictivePopTransitionSpec = { animation },
        )
    }
}
