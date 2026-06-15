package com.shrmrm.ft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import com.shrmrm.ft.navigation.AppNavigator
import com.shrmrm.ft.navigation.RootNavigation
import com.shrmrm.ft.navigation.Routes
import com.shrmrm.ft.ui.theme.FTTheme
import dagger.hilt.android.AndroidEntryPoint

val LocalAppNavigator =
    staticCompositionLocalOf<AppNavigator>
    { error("No appNavigator provided!") }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FTTheme {
                val appNavigator = remember { AppNavigator(Routes.ExpensesRoute) }
                CompositionLocalProvider(LocalAppNavigator provides appNavigator) {
                    RootNavigation()
                }
            }
        }
    }
}
