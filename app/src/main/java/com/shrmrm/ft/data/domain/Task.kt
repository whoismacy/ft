package com.shrmrm.ft.data.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    val id: Int = 1,
    @ColumnInfo(name = "task_name") val name: String,
    @ColumnInfo(
        name = "created_at",
        defaultValue = "CURRENT_TIMESTAMP",
    ) val created: Date,
)
