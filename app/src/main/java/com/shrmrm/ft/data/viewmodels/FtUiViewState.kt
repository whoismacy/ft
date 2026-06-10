package com.shrmrm.ft.data.viewmodels

import com.shrmrm.ft.data.domain.Expense
import com.shrmrm.ft.data.domain.Task
import com.shrmrm.ft.data.domain.TaskLog

data class FtUiViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val tasks: List<Task> = emptyList(),
    val expenses: List<Expense> = emptyList(),
    val taskLogs: List<TaskLog> = emptyList(),
)
