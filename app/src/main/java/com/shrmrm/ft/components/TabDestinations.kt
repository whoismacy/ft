package com.shrmrm.ft.components

import com.shrmrm.ft.navigation.Routes

enum class TabDestinations(
    val value: String,
    val route: Routes,
) {
    TODAY("Today", Routes.ExpensesRoute),
    YESTERDAY("Yesterday", Routes.YesterdayExpensesRoute),
    PAST_WEEK("Past Week", Routes.PastWeekExpensesRoute),
    MORE("More", Routes.MoreExpensesRoute),
}
