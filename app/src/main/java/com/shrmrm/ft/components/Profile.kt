package com.shrmrm.ft.components

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.shrmrm.ft.R
import com.shrmrm.ft.data.domain.User
import com.shrmrm.ft.data.viewmodels.FtIntent
import com.shrmrm.ft.data.viewmodels.FtViewModel
import com.shrmrm.ft.utils.saveImageToInternalStorage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsProfile(viewModel: FtViewModel) {
    val user =
        viewModel
            .getUser()
            .collectAsStateWithLifecycle(initialValue = null)
            .value
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
                .padding()
                .pointerInput(Unit) {
                    detectTapGestures(onPress = {
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
            Avatar(user?.imageUrl)
            Column {
                Text(
                    user?.name ?: "Null",
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
            user = user,
            viewModel = viewModel,
            onDismissRequest = { displayModalBottomSheet = false },
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Avatar(imageUrl: String? = null) {
    Box(
        modifier =
            Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center,
    ) {
        if (imageUrl.isNullOrEmpty()) {
            Icon(
                painter = painterResource(R.drawable.outline_person_off_24),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        } else {
            GlideImage(
                model = imageUrl,
                contentDescription = "Profile picture",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                failure = placeholder(R.drawable.outline_person_off_24),
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeProfileDetails(
    user: User?,
    viewModel: FtViewModel,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState =
            rememberBottomSheetState(
                initialValue = SheetValue.Hidden,
                enabledValues =
                    setOf(SheetValue.Hidden, SheetValue.Expanded),
            ),
        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
        contentColor = MaterialTheme.colorScheme.onSurface,
        shape = MaterialTheme.shapes.medium,
        properties = ModalBottomSheetProperties(shouldDismissOnBackPress = true),
    ) {
        val context = LocalContext.current
        var username by remember(user) { mutableStateOf(user?.name ?: "") }
        var imageUrl by remember(user) { mutableStateOf(user?.imageUrl ?: "") }
        val isSaveButtonEnabled =
            (username.isNotEmpty() && user?.name != username) ||
                (imageUrl.isNotEmpty() && user?.imageUrl != imageUrl)
        val pickMedia =
            rememberLauncherForActivityResult(
                ActivityResultContracts
                    .PickVisualMedia(),
            ) { uri ->
                if (uri != null) {
                    val permanentPath = saveImageToInternalStorage(context, uri)
                    if (permanentPath != null) {
                        imageUrl = uri.toString()
                    }
                }
            }

        Box(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Change Profile Details", style = MaterialTheme.typography.displaySmall)
                Avatar(imageUrl)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        if (user?.imageUrl == null) "Add Image" else "Change Image",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    TextButton(
                        onClick = {
                            pickMedia.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts
                                        .PickVisualMedia
                                        .ImageOnly,
                                ),
                            )
                        },
                        border =
                            BorderStroke(
                                width = 0.5.dp,
                                color =
                                    MaterialTheme
                                        .colorScheme.outlineVariant
                                        .copy(alpha = 0.8f),
                            ),
                    ) {
                        Text(
                            "Select Photo",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "CHANGE USERNAME",
                        letterSpacing = 1.9.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodySmallEmphasized,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                    )
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.extraLarge,
                        maxLines = 1,
                        keyboardOptions =
                            KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                autoCorrectEnabled = true,
                                keyboardType = KeyboardType.Text,
                            ),
                    )
                }

                Button(
                    onClick = {
                        viewModel.handleIntent(
                            FtIntent.UpsertUser(
                                User(
                                    id = 1,
                                    name = username,
                                    imageUrl = imageUrl,
                                ),
                            ),
                        )
                        onDismissRequest()
                    },
                    enabled = isSaveButtonEnabled,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painterResource(R.drawable.baseline_save_24),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                        )
                        Spacer(Modifier.width(24.dp))
                        Text(
                            "Apply Changes",
                            style = MaterialTheme.typography.bodyLargeEmphasized,
                        )
                    }
                }
            }
        }
    }
}
