package com.shrmrm.ft.data.viewmodels

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
        val id: Int,
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
        val id: Int,
        val status: String,
    ) : FtIntent()
}
