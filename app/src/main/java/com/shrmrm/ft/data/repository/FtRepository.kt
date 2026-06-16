package com.shrmrm.ft.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.shrmrm.ft.data.db.FtDao
import java.time.Instant
import javax.inject.Inject

class FtRepository
    @Inject
    constructor(
        private val ftDao: FtDao,
    ) {
        // TASKS
        suspend fun createTask(name: String) {
            ftDao.createTask(name)
        }

        fun loadAllTasks() = ftDao.loadAllTasks()

        fun getExpensesInRange(
            start: Instant,
            end: Instant,
        ) = ftDao.getExpenseInRange(start, end)

        fun updateTask(
            id: Int,
            name: String,
        ) {
            ftDao.updateTask(id, name)
        }

        fun deleteTask(id: Int) {
            ftDao.deleteTask(id)
        }

        // EXPENSES
        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun createExpense(
            name: String,
            amount: Int,
            date: Instant = Instant.now(),
        ) {
            ftDao.createExpense(name, amount, date)
        }

        fun loadAllExpenses() = ftDao.loadAllExpenses()

        fun updateExpense(
            id: Int,
            value: Int,
        ) {
            ftDao.updateExpense(id, value)
        }

        fun deleteExpense(id: Int) {
            ftDao.deleteExpense(id)
        }

        // TASK LOGS

        fun loadAllTaskLogs() = ftDao.loadAllTaskLogs()

        suspend fun completeTask(
            id: Int,
            status: String = "DONE",
        ) {
            ftDao.completeTask(id, status)
        }
    }
