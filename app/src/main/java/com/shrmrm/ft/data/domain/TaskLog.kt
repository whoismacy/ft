package com.shrmrm.ft.data.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import java.time.Instant

@Entity(
    tableName = "tasks_logs",
    primaryKeys = ["task_log_id", "log_date"],
    foreignKeys = [
        ForeignKey(
            entity = Task::class,
            parentColumns = ["task_id"],
            childColumns = ["task_log_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class TaskLog(
    @ColumnInfo(name = "task_log_id")
    val id: Int = 0,
    @ColumnInfo(
        name = "task_status",
        defaultValue = "'DONE'",
    ) val status: String,
    @ColumnInfo(
        name = "log_date",
    ) val logDate: Instant,
)
