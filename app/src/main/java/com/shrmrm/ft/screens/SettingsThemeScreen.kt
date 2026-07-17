package com.shrmrm.ft.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shrmrm.ft.LocalThemeViewModel
import com.shrmrm.ft.R
import com.shrmrm.ft.data.viewmodels.AppThemeMode

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsThemeScreen() {
    val themeViewModel = LocalThemeViewModel.current
    val themeMode =
        themeViewModel
            .themeMode
            .collectAsStateWithLifecycle()
            .value
    val dynamicModeEnabled =
        themeViewModel
            .dynamicModeEnabled
            .collectAsStateWithLifecycle()
            .value

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            "Appearance",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Card(
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                ),
            shape = RoundedCornerShape(24.dp),
        ) {
            ListItem(
                modifier = Modifier.clickable { themeViewModel.setDynamicMode(value = !dynamicModeEnabled) },
                headlineContent = { Text("Dynamic Colour", fontWeight = FontWeight.Bold) },
                supportingContent = { Text(("Match app colours to your wallpaper")) },
                leadingContent = {
                    Icon(
                        painter = painterResource(R.drawable.baseline_palette_24),
                        contentDescription = null,
                    )
                },
                trailingContent = {
                    Switch(
                        checked = dynamicModeEnabled,
                        onCheckedChange = { themeViewModel.setDynamicMode(value = !dynamicModeEnabled) },
                    )
                },
                colors =
                    ListItemDefaults.colors(
                        containerColor = Color.Transparent,
                    ),
            )
        }
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            AppThemeMode.entries.forEachIndexed { index, mode ->
                SegmentedButton(
                    selected = mode == themeMode,
                    onClick = { themeViewModel.setThemeMode(mode) },
                    shape = SegmentedButtonDefaults.itemShape(index, AppThemeMode.entries.size),
                    label = { Text(mode.name) },
                )
            }
        }
    }
}
