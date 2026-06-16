package com.shrmrm.ft.data.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
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
import java.time.Instant
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class FtViewModel
    @Inject
    constructor(
        private val repo: FtRepository,
    ) : ViewModel() {
        private val _ftUiViewState = MutableStateFlow(FtUiViewState())
        val ftUiViewState: StateFlow<FtUiViewState> = _ftUiViewState.asStateFlow()

        private val _snackBarHost = mutableStateOf(SnackbarHostState())
        val snackBarHost = _snackBarHost.value

        init {
            handleIntent(FtIntent.LoadAll)
        }

        @RequiresApi(Build.VERSION_CODES.O)
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

        fun getExpenseInRange(
            start: Instant,
            end: Instant,
        ) = repo.getExpensesInRange(start, end)

        private fun changeLoading(state: Boolean) {
            _ftUiViewState.value = _ftUiViewState.value.copy(isLoading = state)
        }

        private fun triggerEvent(message: String) {
            EventManager.triggerEvent(EventManager.AppEvent.ShowSnackbar(message))
        }

        private fun loadAll() {
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
            changeLoading(true)
            try {
                repo.createTask(name)
                triggerEvent("Task Successfully added🎉")
                changeLoading(false)
            } catch (_: Exception) {
                triggerEvent("An error occurred while creating a new Task! ❌")
                changeLoading(false)
            }
        }

        private fun updateTask(
            id: Int,
            name: String,
        ) {
            changeLoading(true)
            try {
                repo.updateTask(id, name)
                changeLoading(false)
                triggerEvent("Task successfully updated🎉")
            } catch (_: Exception) {
                changeLoading(false)
                triggerEvent("An error occurred while updating Task!")
            }
        }

        private fun deleteTask(id: Int) {
            changeLoading(true)
            try {
                repo.deleteTask(id)
                changeLoading(false)
                triggerEvent("Task successfully deleted🎉")
            } catch (_: Exception) {
                triggerEvent("An error occurred while deleting Task")
                changeLoading(false)
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private suspend fun createNewExpense(
            name: String,
            amount: Int,
        ) {
            changeLoading(true)
            try {
                repo.createExpense(name, amount)
                triggerEvent("Expense Successfully added")
                changeLoading(false)
            } catch (_: Exception) {
                triggerEvent("An error occurred while creating a new Expense!")
                changeLoading(false)
            }
        }

        private fun updateExpense(
            id: Int,
            value: Int,
        ) {
            changeLoading(true)
            try {
                repo.updateExpense(id, value)
                changeLoading(false)
            } catch (_: Exception) {
                triggerEvent("An error occurred while updating Expense!")
                changeLoading(false)
            }
        }

        private fun deleteExpense(id: Int) {
            changeLoading(true)
            try {
                repo.deleteExpense(id)
                triggerEvent("Expense successfully deleted 🎉")
                changeLoading(false)
            } catch (_: Exception) {
                triggerEvent("An error occurred while deleting Expense!")
                changeLoading(false)
            }
        }

        private fun completeTask(
            id: Int,
            status: String,
        ) {
            changeLoading(true)
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
                        changeLoading(false)
                    } catch (_: Exception) {
                        triggerEvent("An error occurred while completing Task!")
                        changeLoading(false)
                    }
                }
            }
        }
    }
