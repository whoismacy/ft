package com.shrmrm.ft.data.repository

import com.shrmrm.ft.data.db.FtDao
import java.util.Date
import javax.inject.Inject

class FtRepository
    @Inject
    constructor(
        private val ftDao: FtDao,
    ) {
        // INSERTION
        suspend fun insertTask(name: String) {
            ftDao.insertTask(name)
        }

        suspend fun insertExpense(
            id: Int,
            name: String,
            amount: Int,
            date: Date = Date(),
        ) {
            ftDao.insertExpense(id, name, amount, date)
        }

        suspend fun completeTask(
            id: Int,
            status: String = "DONE",
        ) {
            ftDao.completeTask(id, status)
        }

        // DELETION
        // UPDATE
        // FETCHING
        fun loadAllExpenses() = ftDao.loadAllExpenses()

        fun loadAllTasks() = ftDao.loadAllTasks()

        fun loadAllTaskLogs() = ftDao.loadAllTaskLogs()
    }
