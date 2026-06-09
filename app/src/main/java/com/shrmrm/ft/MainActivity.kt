package com.shrmrm.ft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.shrmrm.ft.navigation.RootNavigation
import com.shrmrm.ft.ui.theme.FTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FTTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RootNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
