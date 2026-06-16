package com.shrmrm.ft.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.shrmrm.ft.data.viewmodels.FtViewModel

@Composable
fun PastWeekExpensesScreen(
    viewModel: FtViewModel,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text("Under Construction")
    }
}
