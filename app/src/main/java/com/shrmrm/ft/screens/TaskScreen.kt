package com.shrmrm.ft.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shrmrm.ft.data.viewmodels.FtViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    viewModel: FtViewModel,
) {
    val loadingState =
        viewModel
            .loading
            .collectAsStateWithLifecycle()
            .value

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        if (loadingState) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                ContainedLoadingIndicator(
                    Modifier
                        .size(64.dp)
                        .align(Alignment.Center),
                )
            }
        } else {
            Text("TaskScreen under construction")
        }
    }
}
