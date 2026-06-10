package com.shrmrm.ft.data.db

import androidx.room.Dao
import androidx.room.Query
import com.shrmrm.ft.data.domain.Expense
import com.shrmrm.ft.data.domain.Task
import com.shrmrm.ft.data.domain.TaskLog
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface FtDao {
    @Query("INSERT INTO 'expenses' (expense_id, expense_name, expense_amount, expense_date) VALUES (:id, :name, :amount, :date); ")
    suspend fun insertExpense(
        id: Int,
        name: String,
        amount: Int,
        date: Date = Date(),
    )

    @Query("SELECT * FROM `expenses`;")
    fun loadAllExpenses(): Flow<List<Expense>>

    @Query("INSERT INTO `tasks` (task_name) VALUES(:name);")
    suspend fun insertTask(name: String)

    @Query("SELECT * FROM `tasks`;")
    fun loadAllTasks(): Flow<List<Task>>

    @Query("INSERT INTO `tasks_logs` (task_log_id, task_status) VALUES (:id, :status);")
    suspend fun completeTask(
        id: Int,
        status: String = "DONE",
    )

    @Query("SELECT * FROM `tasks_logs`;")
    fun loadAllTaskLogs(): Flow<List<TaskLog>>
}
