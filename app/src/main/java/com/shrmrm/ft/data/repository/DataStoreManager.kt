package com.shrmrm.ft.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.shrmrm.ft.data.viewmodels.AppThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settingsTheme")

class DataStoreManager(
    private val context: Context,
) {
    companion object {
        val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
        val DYNAMIC_MODE_KEY = booleanPreferencesKey("dynamic_mode")
    }

    val themeMode: Flow<AppThemeMode> =
        context.dataStore.data.map { prefs ->
            val name = prefs[THEME_MODE_KEY] ?: AppThemeMode.SYSTEM.name
            AppThemeMode.valueOf(name)
        }

    suspend fun setThemeMode(mode: AppThemeMode) {
        context.dataStore.edit { it[THEME_MODE_KEY] = mode.name }
    }

    suspend fun setDynamicModeEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DYNAMIC_MODE_KEY] = enabled
        }
    }

    val isDynamicModeEnabled: Flow<Boolean> =
        context
            .dataStore.data
            .map { prefs -> prefs[DYNAMIC_MODE_KEY] ?: false }
}
