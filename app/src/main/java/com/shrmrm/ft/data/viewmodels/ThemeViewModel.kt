package com.shrmrm.ft.data.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemeViewModel : ViewModel() {
    private val _themeMode = MutableStateFlow(AppThemeMode.SYSTEM)
    val themeMode: StateFlow<AppThemeMode> = _themeMode

    private val _dynamicColour = MutableStateFlow(true)
    val dynamicColour: StateFlow<Boolean> = _dynamicColour

    fun setThemeMode(mode: AppThemeMode) {
        _themeMode.value = mode
    }

    fun toggleDynamicColour() {
        _dynamicColour.value = !_dynamicColour.value
    }
}
