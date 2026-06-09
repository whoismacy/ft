package com.shrmrm.ft.data.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TaskLogDao {
    @Query("INSERT INTO `tasks_logs` (task_log_id, task_status) VALUES (:id, :status);")
    suspend fun completeTask(
        id: Int,
        status: String = "DONE",
    )
}
