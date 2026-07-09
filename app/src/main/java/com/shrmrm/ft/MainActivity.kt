package com.shrmrm.ft

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shrmrm.ft.data.viewmodels.ThemeViewModel
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
    private val themeViewModel by viewModels<ThemeViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by themeViewModel
                .themeMode
                .collectAsStateWithLifecycle()
            val dynamicColor by themeViewModel
                .dynamicColour
                .collectAsStateWithLifecycle()
            FTTheme(
                themeMode = themeMode,
                dynamicColor = dynamicColor,
            ) {
                val appNavigator =
                    remember {
                        AppNavigator(Routes.ExpensesRoute)
                    }
                CompositionLocalProvider(LocalAppNavigator provides appNavigator) {
                    RootNavigation(themeViewModel)
                }
            }
        }
    }
}
