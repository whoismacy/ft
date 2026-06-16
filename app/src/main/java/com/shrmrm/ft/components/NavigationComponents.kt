package com.shrmrm.ft.components

import com.shrmrm.ft.R
import com.shrmrm.ft.navigation.Routes

enum class NavigationComponents(
    val label: String,
    val icon: Int,
    val route: Routes,
) {
    Expense(
        "Expenses",
        R.drawable.outline_family_home_24,
        Routes.ExpensesRoute,
    ),
    Tasks(
        "Tasks",
        R.drawable.outline_person_heart_24,
        Routes.TasksRoute,
    ),
}
