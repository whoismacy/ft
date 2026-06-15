package com.shrmrm.ft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.shrmrm.ft.data.events.EventManager
import com.shrmrm.ft.navigation.RootNavigation
import com.shrmrm.ft.ui.theme.FTTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FTTheme {
                val coroutineScope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(EventManager) {
                    lifecycleScope.launch {
                        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
                    }
                }
                RootNavigation()
            }
        }
    }
}
