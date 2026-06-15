package com.shrmrm.ft.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun EmptyState(
    message: String,
    modifier: Modifier = Modifier,
    supportingMessage: String? = null,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                message,
                style = MaterialTheme.typography.bodyLargeEmphasized,
            )
            if (!supportingMessage.isNullOrEmpty()) {
                Text(
                    supportingMessage,
                    style = MaterialTheme.typography.bodySmallEmphasized,
                )
            }
        }
    }
}
