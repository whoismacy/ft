package com.shrmrm.ft.data.viewmodels

import androidx.lifecycle.ViewModel
import com.shrmrm.ft.data.repository.FtRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FtViewModel
    @Inject
    constructor(
        private val repository: FtRepository,
    ) : ViewModel() {
        private val _loading = MutableStateFlow(true)
        val loading = _loading.asStateFlow()
    }
