package com.shrmrm.ft.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shrmrm.ft.R
import com.shrmrm.ft.data.viewmodels.FtViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsProfile(viewModel: FtViewModel) {
    val totalExpenses =
        viewModel.ftUiViewState
            .collectAsStateWithLifecycle()
            .value.expenses.size
    val totalTasks =
        viewModel.ftUiViewState
            .collectAsStateWithLifecycle()
            .value.tasks.size
    var displayModalBottomSheet by remember { mutableStateOf(false) }
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onLongPress = {
                        displayModalBottomSheet = true
                    })
                },
        shape = RoundedCornerShape(24.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            ),
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Avatar()
            Column {
                Text(
                    "John Doe",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    "$totalTasks tasks • $totalExpenses expenses",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
    if (displayModalBottomSheet) {
        ChangeProfileDetails(
            onDismissRequest = { displayModalBottomSheet = false },
            viewModel = viewModel,
        )
    }
}

@Composable
fun Avatar() {
    Box(
        modifier =
            Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.outline_person_off_24),
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeProfileDetails(
    onDismissRequest: () -> Unit,
    viewModel: FtViewModel,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberBottomSheetState(initialValue = SheetValue.Hidden),
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        shape = MaterialTheme.shapes.medium,
        properties = ModalBottomSheetProperties(shouldDismissOnBackPress = true),
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            Text(
                "Under construction 🏗️🏗️",
                style = MaterialTheme.typography.displaySmall,
            )
        }
    }
}
