package com.shrmrm.ft.data.db

import androidx.room.Dao
import androidx.room.Query
import com.shrmrm.ft.data.domain.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("INSERT INTO `tasks` (task_name) VALUES(:name);")
    suspend fun insertTask(name: String)

    @Query("SELECT * FROM `tasks`;")
    fun loadAllTasks(): Flow<List<Task>>
}
