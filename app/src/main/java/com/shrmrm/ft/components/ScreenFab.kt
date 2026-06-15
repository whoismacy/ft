package com.shrmrm.ft.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScreenFab(
    text: String = "New",
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Row {
            Text(text)
            Text("+", style = MaterialTheme.typography.bodyMediumEmphasized)
        }
    }
}
