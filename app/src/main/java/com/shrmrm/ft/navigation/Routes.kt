package com.shrmrm.ft.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {
    @Serializable
    data object ExpensesRoute : Routes, NavKey

    @Serializable
    data object TasksRoute : Routes, NavKey

    @Serializable
    data object TodaysExpensesRoute : Routes, NavKey

    @Serializable
    data object YesterdayExpensesRoute : Routes, NavKey

    @Serializable
    data object PastWeekExpensesRoute : Routes, NavKey

    @Serializable
    data object MoreExpensesRoute : Routes, NavKey
}

class AppNavigator(
    initialRoute: Routes,
) {
    val backStack = mutableStateListOf<Any>(initialRoute)

    fun navigateTo(route: Any) {
        backStack.add(route)
    }

    fun navigateBack() {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }

    fun popToRoot() {
        if (backStack.size > 1) {
            backStack.removeRange(1, backStack.size)
        }
    }
}
