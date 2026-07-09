package com.shrmrm.ft.screens

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
import com.shrmrm.ft.R
import com.shrmrm.ft.data.viewmodels.AppThemeMode
import com.shrmrm.ft.data.viewmodels.ThemeViewModel

@Composable
fun SettingsThemeScreen(themeViewModel: ThemeViewModel) {
    val currentTheme =
        themeViewModel
            .themeMode
            .collectAsStateWithLifecycle()
            .value
    val dynamicColour =
        themeViewModel
            .dynamicColour
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
                modifier = Modifier.clickable { themeViewModel.toggleDynamicColour() },
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
                        checked = dynamicColour,
                        onCheckedChange = { themeViewModel.toggleDynamicColour() },
                    )
                },
                colors =
                    ListItemDefaults.colors(
                        containerColor = Color.Transparent,
                    ),
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Theme Mode", style = MaterialTheme.typography.labelLarge)
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                AppThemeMode.entries.forEachIndexed { index, mode ->
                    SegmentedButton(
                        selected = currentTheme == mode,
                        onClick = { themeViewModel.setThemeMode(mode) },
                        shape =
                            SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = AppThemeMode.entries.size,
                            ),
                        icon = {},
                        label = {
                            Text(mode.value)
                        },
                    )
                }
            }
        }
    }
}
