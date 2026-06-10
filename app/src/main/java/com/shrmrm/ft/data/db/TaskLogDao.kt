package com.shrmrm.ft.data.db

import androidx.room.Dao
import androidx.room.Query
import com.shrmrm.ft.data.domain.TaskLog
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskLogDao {
    @Query("INSERT INTO `tasks_logs` (task_log_id, task_status) VALUES (:id, :status);")
    suspend fun completeTask(
        id: Int,
        status: String = "DONE",
    )

    @Query("SELECT * FROM `tasks_logs`;")
    fun loadAllTaskLogs(): Flow<List<TaskLog>>
}
