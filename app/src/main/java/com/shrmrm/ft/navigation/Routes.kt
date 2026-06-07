package com.shrmrm.ft.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {
    @Serializable
    data object HomeRoute : Routes, NavKey

    @Serializable
    data object TasksRoute : Routes, NavKey
}
