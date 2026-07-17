package com.shrmrm.ft.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shrmrm.ft.data.repository.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel
    @Inject
    constructor(
        private val dataStoreManager: DataStoreManager,
    ) : ViewModel() {
        private val _dynamicModeEnabled =
            dataStoreManager.isDynamicModeEnabled.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                false,
            )
        val dynamicModeEnabled = _dynamicModeEnabled

        val themeMode =
            dataStoreManager.themeMode.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                AppThemeMode.SYSTEM,
            )

        fun setThemeMode(value: AppThemeMode) {
            viewModelScope.launch {
                dataStoreManager.setThemeMode(value)
            }
        }

        fun setDynamicMode(value: Boolean) {
            viewModelScope.launch {
                dataStoreManager.setDynamicModeEnabled(value)
            }
        }
    }
