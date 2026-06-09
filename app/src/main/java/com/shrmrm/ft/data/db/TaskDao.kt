package com.shrmrm.ft.data.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TaskDao {
    @Query("INSERT INTO `tasks` (task_name) VALUES(:name);")
    suspend fun insertTask(name: String)
}
