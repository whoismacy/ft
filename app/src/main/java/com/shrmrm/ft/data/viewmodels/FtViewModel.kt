package com.shrmrm.ft.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shrmrm.ft.data.repository.FtRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FtViewModel
    @Inject
    constructor(
        private val repo: FtRepository,
    ) : ViewModel() {
        private val _loading = MutableStateFlow(true)
        val loading = _loading.asStateFlow()

        private val _ftUiViewState = MutableStateFlow(FtUiViewState())
        val ftUiViewState: StateFlow<FtUiViewState> = _ftUiViewState.asStateFlow()

        init {
            handleIntent(FtIntent.LoadAll)
        }

        fun handleIntent(intent: FtIntent) {
            viewModelScope.launch {
                when (intent) {
                    is FtIntent.LoadAll -> {
                        loadAll()
                    }

                    //
                    is FtIntent.DeleteExpense -> {}

                    is FtIntent.DeleteTask -> {}

                    //
                    is FtIntent.UpdateExpense -> {}

                    is FtIntent.UpdateTask -> {}
                }
            }
        }

        fun loadAll() {
            _ftUiViewState.value = _ftUiViewState.value.copy(isLoading = true)
            combine(
                repo.loadAllTasks(),
                repo.loadAllExpenses(),
                repo.loadAllTaskLogs(),
            ) { tasks, expenses, taskLogs ->
                FtUiViewState(
                    isLoading = true,
                    error = null,
                    tasks = tasks,
                    expenses = expenses,
                    taskLogs = taskLogs,
                )
            }.onEach { state ->
                _ftUiViewState.value = state
            }.launchIn(viewModelScope)
        }

        fun inserTask(name: String) {
            viewModelScope.launch {
                repo.insertTask(name)
            }
        }
    }
