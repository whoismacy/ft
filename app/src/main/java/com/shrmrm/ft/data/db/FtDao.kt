package com.shrmrm.ft.data.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.shrmrm.ft.data.domain.Expense
import com.shrmrm.ft.data.domain.Task
import com.shrmrm.ft.data.domain.TaskLog
import kotlinx.coroutines.flow.Flow
import java.time.Instant

@Dao
interface FtDao {
    // TASKS

    @RequiresApi(Build.VERSION_CODES.O)
    @Query("INSERT INTO `tasks` (task_name, created_at) VALUES (:name, :date);")
    suspend fun createTask(
        name: String,
        date: Instant = Instant.now(),
    )

    @Query("SELECT * FROM `tasks`;")
    fun loadAllTasks(): Flow<List<Task>>

    @Query("UPDATE `tasks` SET `task_name` = :name WHERE `task_id` = :id")
    fun updateTask(
        id: Int,
        name: String,
    )

    @Query("DELETE FROM `tasks` WHERE `task_id` = :id;")
    fun deleteTask(id: Int)

    // EXPENSES
    @RequiresApi(Build.VERSION_CODES.O)
    @Query("INSERT INTO 'expenses' (expense_name, expense_amount, expense_date) VALUES (:name, :amount, :date); ")
    suspend fun createExpense(
        name: String,
        amount: Int,
        date: Instant = Instant.now(),
    )

    @Query("SELECT * FROM `expenses` WHERE `expense_date` >= :start AND `expense_date` < :end; ")
    fun getExpenseInRange(
        start: Instant,
        end: Instant,
    ): Flow<List<Expense>>

    @Query("SELECT * FROM `expenses`;")
    fun loadAllExpenses(): Flow<List<Expense>>

    @Query("UPDATE `expenses` SET `expense_amount` = :value WHERE `expense_id` = :id;")
    fun updateExpense(
        id: Int,
        value: Int,
    )

    @Query("DELETE FROM `expenses` WHERE `expense_id` = :id;")
    fun deleteExpense(id: Int)

    // TASK LOGS
    @Query("SELECT * FROM `tasks_logs`;")
    fun loadAllTaskLogs(): Flow<List<TaskLog>>

    @Query("SELECT * FROM `tasks_logs` WHERE `task_log_id` = :id;")
    fun getTaskLog(id: Int): Flow<TaskLog?>

    @Upsert
    suspend fun completeTask(taskLog: TaskLog)
}
