package com.shrmrm.ft.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shrmrm.ft.data.domain.TaskState
import com.shrmrm.ft.data.events.EventManager
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
                    // get
                    is FtIntent.LoadAll -> {
                        loadAll()
                    }

                    // delete
                    is FtIntent.DeleteExpense -> {}

                    is FtIntent.DeleteTask -> {}

                    // update
                    is FtIntent.UpdateExpense -> {}

                    is FtIntent.UpdateTask -> {}

                    is FtIntent.CompleteTask -> {
                        completeTask(
                            intent.id,
                            intent.status,
                        )
                    }

                    // create
                    is FtIntent.CreateTask -> {
                        insertTask(intent.name)
                    }

                    is FtIntent.CreateExpense -> {
                        insertExpense(
                            intent.id,
                            intent.name,
                            intent.amount,
                        )
                    }
                }
            }
        }

        private fun triggerEvent(message: String) {
            EventManager.triggerEvent(EventManager.AppEvent.ShowSnackbar(message))
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

        fun insertTask(name: String) {
            viewModelScope.launch {
                try {
                    repo.insertTask(name)
                } catch (_: Exception) {
                    triggerEvent("An error occurred while creating a new Task!")
                }
            }
        }

        fun insertExpense(
            id: Int,
            name: String,
            amount: Int,
        ) {
            viewModelScope.launch {
                try {
                    repo.insertExpense(id, name, amount)
                } catch (_: Exception) {
                    triggerEvent("An error occurred while creating a new Expense!")
                }
            }
        }

        fun completeTask(
            id: Int,
            status: String,
        ) {
            val validStatus =
                TaskState
                    .entries
                    .map { it.name }

            if (status !in validStatus) {
                triggerEvent("An error occurred while completing Task!")
            } else {
                viewModelScope.launch {
                    try {
                        repo.completeTask(id, status)
                    } catch (_: Exception) {
                        triggerEvent("An error occurred while completing Task!")
                    }
                }
            }
        }
    }
