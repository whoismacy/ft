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
                    is FtIntent.LoadAll -> {
                        loadAll()
                    }

                    is FtIntent.CreateTask -> {
                        createNewTask(intent.name)
                    }

                    is FtIntent.UpdateTask -> {
                        updateTask(intent.id, intent.name)
                    }

                    is FtIntent.DeleteTask -> {
                        deleteTask(intent.id)
                    }

                    is FtIntent.CreateExpense -> {
                        createNewExpense(
                            intent.id,
                            intent.name,
                            intent.amount,
                        )
                    }

                    is FtIntent.UpdateExpense -> {
                        updateExpense(intent.id, intent.value)
                    }

                    is FtIntent.DeleteExpense -> {
                        deleteExpense(intent.id)
                    }

                    is FtIntent.CompleteTask -> {
                        completeTask(
                            intent.id,
                            intent.status,
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

        fun createNewTask(name: String) {
            viewModelScope.launch {
                try {
                    repo.createTask(name)
                } catch (_: Exception) {
                    triggerEvent("An error occurred while creating a new Task!")
                }
            }
        }

        fun updateTask(
            id: Int,
            name: String,
        ) {
            viewModelScope.launch {
                try {
                    repo.updateTask(id, name)
                } catch (_: Exception) {
                    triggerEvent("An error occurred while updating Task!")
                }
            }
        }

        fun deleteTask(id: Int) {
            viewModelScope.launch {
                try {
                    repo.deleteTask(id)
                } catch (_: Exception) {
                    triggerEvent("An error occurred while deleting Task")
                }
            }
        }

        fun createNewExpense(
            id: Int,
            name: String,
            amount: Int,
        ) {
            viewModelScope.launch {
                try {
                    repo.createExpense(id, name, amount)
                } catch (_: Exception) {
                    triggerEvent("An error occurred while creating a new Expense!")
                }
            }
        }

        fun updateExpense(
            id: Int,
            value: Int,
        ) {
            viewModelScope.launch {
                try {
                    repo.updateExpense(id, value)
                } catch (_: Exception) {
                    triggerEvent("An error occurred while updating Expense!")
                }
            }
        }

        fun deleteExpense(id: Int) {
            viewModelScope.launch {
                try {
                    repo.deleteExpense(id)
                } catch (_: Exception) {
                    triggerEvent("An error occurred while deleting Expense!")
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
