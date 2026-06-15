package com.shrmrm.ft.components

import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.shrmrm.ft.R

@Composable
fun ScreenFab(
    modifier: Modifier = Modifier,
    text: String = "New",
    onClick: () -> Unit,
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        text = { Text(text) },
        modifier = modifier,
        icon = {
            Icon(
                painter =
                    painterResource(R.drawable.outline_add_24),
                contentDescription = null,
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    )
}
