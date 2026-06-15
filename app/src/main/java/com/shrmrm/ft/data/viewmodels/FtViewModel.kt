package com.shrmrm.ft.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shrmrm.ft.data.domain.TaskState
import com.shrmrm.ft.data.events.EventManager
import com.shrmrm.ft.data.repository.FtRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
        private val _ftUiViewState = MutableStateFlow(FtUiViewState())
        val ftUiViewState: StateFlow<FtUiViewState> = _ftUiViewState.asStateFlow()

        init {
            handleIntent(FtIntent.LoadAll)
        }

        fun handleIntent(intent: FtIntent) {
            viewModelScope.launch(Dispatchers.IO) {
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
            combine(
                repo.loadAllTasks(),
                repo.loadAllExpenses(),
                repo.loadAllTaskLogs(),
            ) { tasks, expenses, taskLogs ->
                _ftUiViewState.value.copy(
                    isLoading = false,
                    tasks = tasks,
                    expenses = expenses,
                    taskLogs = taskLogs,
                )
            }.onEach { state ->
                _ftUiViewState.value = state
            }.launchIn(viewModelScope)
        }

        private suspend fun createNewTask(name: String) {
            _ftUiViewState.value = _ftUiViewState.value.copy(isLoading = true)
            try {
                repo.createTask(name)
                triggerEvent("Task Successfully added🎉")
                _ftUiViewState.value = _ftUiViewState.value.copy(isLoading = false)
            } catch (_: Exception) {
                triggerEvent("An error occurred while creating a new Task!")
                _ftUiViewState.value = _ftUiViewState.value.copy(isLoading = false)
            }
        }

        private fun updateTask(
            id: Int,
            name: String,
        ) {
            _ftUiViewState.value = _ftUiViewState.value.copy(isLoading = true)
            try {
                repo.updateTask(id, name)
                _ftUiViewState.value = _ftUiViewState.value.copy(isLoading = false)
            } catch (_: Exception) {
                _ftUiViewState.value = _ftUiViewState.value.copy(isLoading = false)
                triggerEvent("An error occurred while updating Task!")
            }
        }

        private fun deleteTask(id: Int) {
            try {
                repo.deleteTask(id)
            } catch (_: Exception) {
                triggerEvent("An error occurred while deleting Task")
            }
        }

        private suspend fun createNewExpense(
            name: String,
            amount: Int,
        ) {
            _ftUiViewState.value = _ftUiViewState.value.copy(isLoading = true)
            try {
                repo.createExpense(name, amount)
                triggerEvent("Task Successfully created🎉")
                _ftUiViewState.value = _ftUiViewState.value.copy(isLoading = false)
            } catch (_: Exception) {
                triggerEvent("An error occurred while creating a new Expense!")
                _ftUiViewState.value = _ftUiViewState.value.copy(isLoading = false)
            }
        }

        private fun updateExpense(
            id: Int,
            value: Int,
        ) {
            try {
                repo.updateExpense(id, value)
            } catch (_: Exception) {
                triggerEvent("An error occurred while updating Expense!")
            }
        }

        private fun deleteExpense(id: Int) {
            try {
                repo.deleteExpense(id)
            } catch (_: Exception) {
                triggerEvent("An error occurred while deleting Expense!")
            }
        }

        private fun completeTask(
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
