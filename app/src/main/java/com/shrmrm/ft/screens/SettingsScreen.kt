package com.shrmrm.ft.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.shrmrm.ft.R
import com.shrmrm.ft.components.SettingsProfile
import com.shrmrm.ft.data.viewmodels.FtViewModel
import com.shrmrm.ft.data.viewmodels.ThemeViewModel
import com.shrmrm.ft.navigation.Routes

private data class SettingsScreenItems(
    val headline: String,
    val supporting: String,
    val icon: Int,
    val contentColor: Color,
    val containerColor: Color,
    val route: Routes,
)

private val animation =
    fadeIn(tween(300)) togetherWith fadeOut(tween(300))

@Composable
private fun getSettingItems(): List<SettingsScreenItems> =
    listOf(
        SettingsScreenItems(
            "Security",
            "Set a password for app's access",
            icon = R.drawable.baseline_security_24,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            route = Routes.SettingsSecurityRoute,
        ),
        SettingsScreenItems(
            "Theme",
            "Change the app's theme",
            R.drawable.baseline_color_lens_24,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            route = Routes.SettingsThemeRoute,
        ),
        SettingsScreenItems(
            "Export & Import data",
            "Backup or add data to FT",
            R.drawable.outline_file_export_24,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            route = Routes.SettingsExportRoute,
        ),
        SettingsScreenItems(
            "About App",
            "See app's information",
            R.drawable.outline_code_24,
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            route = Routes.SettingsAppRoute,
        ),
    )

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsScreen(
    ftViewModel: FtViewModel,
    themeViewModel: ThemeViewModel,
) {
    val settingsBackStack = remember { mutableStateListOf<Routes>(Routes.SettingsRoute) }
    val navigateTo: (Routes) -> Unit = {
        if (settingsBackStack.lastOrNull() != it) {
            settingsBackStack.add(it)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(ftViewModel.snackBarHost) },
    ) { innerPadding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        innerPadding,
                    ),
            contentAlignment = Alignment.Center,
        ) {
            NavDisplay(
                backStack = settingsBackStack,
                entryDecorators =
                    listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator(),
                    ),
                entryProvider =
                    entryProvider {
                        entry<Routes.SettingsRoute> { SettingsItems(navigateTo, ftViewModel) }
                        entry<Routes.SettingsSecurityRoute> { SettingsSecurityScreen() }
                        entry<Routes.SettingsThemeRoute> { SettingsThemeScreen(themeViewModel) }
                        entry<Routes.SettingsAppRoute> { SettingsAppScreen() }
                        entry<Routes.SettingsExportRoute> { SettingsExportScreen() }
                    },
                transitionSpec = { animation },
                predictivePopTransitionSpec = { animation },
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsItems(
    navigate: (Routes) -> Unit,
    viewModel: FtViewModel,
) {
    val items = getSettingItems()
    LazyColumn(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Text(
                "Settings",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.ExtraBold,
            )
            Spacer(Modifier.height(64.dp))
        }
        item {
            SettingsProfile(viewModel)
            Spacer(Modifier.height(32.dp))
        }
        items(items) { item ->
            val isRoundedTop = items[0] == item
            val isRoundedBottom = items.last() == item
            val itemShapeModifier =
                Modifier.clip(
                    RoundedCornerShape(
                        topStart = if (isRoundedTop) 24.dp else 0.dp,
                        topEnd = if (isRoundedTop) 24.dp else 0.dp,
                        bottomStart = if (isRoundedBottom) 24.dp else 0.dp,
                        bottomEnd = if (isRoundedBottom) 24.dp else 0.dp,
                    ),
                )

            SingleItem(
                icon = item.icon,
                headline = item.headline,
                supporting = item.supporting,
                contentColor = item.contentColor,
                containerColor = item.containerColor,
                modifier = itemShapeModifier,
                onClick = { navigate(item.route) },
            )
        }
    }
}

@Composable
fun SingleItem(
    icon: Int,
    headline: String,
    supporting: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ListItem(
        modifier =
            Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 88.dp)
                .then(modifier.clickable(true, onClick = onClick)),
        headlineContent = {
            Text(
                headline,
                style =
                    MaterialTheme
                        .typography.titleMedium
                        .copy(fontWeight = FontWeight.Bold),
            )
        },
        supportingContent = { Text(supporting) },
        leadingContent = {
            Box(
                modifier =
                    Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(containerColor),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = contentColor,
                )
            }
        },
        tonalElevation = 4.dp,
        shadowElevation = 6.dp,
        colors =
            ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            ),
    )
}
