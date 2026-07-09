package com.shrmrm.ft.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.shrmrm.ft.LocalAppNavigator
import com.shrmrm.ft.components.NavigationComponents
import com.shrmrm.ft.data.events.EventManager
import com.shrmrm.ft.data.viewmodels.FtViewModel
import com.shrmrm.ft.data.viewmodels.ThemeViewModel
import com.shrmrm.ft.screens.ExpenseScreen
import com.shrmrm.ft.screens.SettingsScreen
import com.shrmrm.ft.screens.TaskScreen
import kotlinx.coroutines.launch

private val animation =
    fadeIn(tween(1000)) togetherWith fadeOut(tween(1000))

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavigation(
    themeViewModel: ThemeViewModel,
    viewModel: FtViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = viewModel.snackBarHost
    val navigator = LocalAppNavigator.current
    val navScaffoldState = rememberNavigationSuiteScaffoldState()

    LaunchedEffect(Unit) {
        EventManager.channelFlow.collect { event ->
            when (event) {
                is EventManager.AppEvent.ShowSnackbar -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            event.message,
                            duration = SnackbarDuration.Short,
                        )
                    }
                }
            }
        }
    }

    NavigationSuiteScaffold(
        state = navScaffoldState,
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
                    selected = it.route == navigator.backStack.last(),
                    onClick = { navigator.navigateTo(it.route) },
                )
            }
        },
    ) {
        NavDisplay(
            modifier = Modifier,
            backStack = navigator.backStack,
            entryDecorators =
                listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator(),
                ),
            entryProvider =
                entryProvider {
                    entry<Routes.ExpensesRoute> {
                        ExpenseScreen(
                            viewModel = viewModel,
                        )
                    }
                    entry<Routes.TasksRoute> {
                        TaskScreen(
                            viewModel = viewModel,
                        )
                    }

                    entry<Routes.SettingsRoute> {
                        SettingsScreen(
                            ftViewModel = viewModel,
                            themeViewModel = themeViewModel,
                        )
                    }
                },
            transitionSpec = { animation },
            popTransitionSpec = { animation },
            predictivePopTransitionSpec = { animation },
        )
    }
}
