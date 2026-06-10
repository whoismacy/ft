package com.shrmrm.ft.data.repository

import com.shrmrm.ft.data.db.ExpenseDao
import com.shrmrm.ft.data.db.TaskDao
import com.shrmrm.ft.data.db.TaskLogDao
import java.util.Date
import javax.inject.Inject

class FtRepository
    @Inject
    constructor(
        private val expenseDao: ExpenseDao,
        private val taskDao: TaskDao,
        private val taskLogDao: TaskLogDao,
    ) {
        // INSERTION
        suspend fun insertTask(name: String) {
            taskDao.insertTask(name)
        }

        suspend fun insertExpense(
            id: Int,
            name: String,
            amount: Int,
            date: Date = Date(),
        ) {
            expenseDao.insertExpense(id, name, amount, date)
        }

        suspend fun completeTask(
            id: Int,
            status: String = "DONE",
        ) {
            taskLogDao.completeTask(id, status)
        }

        // DELETION
        // UPDATE
        // FETCHING
        fun loadAllExpenses() = expenseDao.loadAllExpenses()

        fun loadAllTasks() = taskDao.loadAllTasks()

        fun loadAllTaskLogs() = taskLogDao.loadAllTaskLogs()
    }
