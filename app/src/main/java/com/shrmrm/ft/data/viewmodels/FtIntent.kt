package com.shrmrm.ft.data.viewmodels

import com.shrmrm.ft.data.domain.TaskLog
import com.shrmrm.ft.data.domain.User

sealed class FtIntent {
    object LoadAll : FtIntent()

    data class CreateTask(
        val name: String,
    ) : FtIntent()

    data class UpdateTask(
        val id: Int,
        val name: String,
    ) : FtIntent()

    data class DeleteTask(
        val id: Int,
    ) : FtIntent()

    data class CreateExpense(
        val name: String,
        val amount: Int,
    ) : FtIntent()

    data class UpdateExpense(
        val id: Int,
        val value: Int,
    ) : FtIntent()

    data class DeleteExpense(
        val id: Int,
    ) : FtIntent()

    data class CompleteTask(
        val taskLog: TaskLog,
    ) : FtIntent()

    data class UpsertUser(
        val user: User,
    ) : FtIntent()
}
