package com.shrmrm.ft.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.shrmrm.ft.screens.HomeScreen
import com.shrmrm.ft.screens.TaskScreen

@Composable
fun RootNavigation(modifier: Modifier = Modifier) {
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
                    HomeScreen(
                        onNavigateToTrendsScreen = { backStack.add(Routes.HomeRoute) },
                        modifier,
                    )
                }
                entry<Routes.TasksRoute> {
                    TaskScreen(modifier)
                }
            },
        transitionSpec = {
            fadeIn(tween(3000)) togetherWith fadeOut(tween(3000))
        },
    )
}
