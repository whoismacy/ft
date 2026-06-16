package com.shrmrm.ft.data.db

import androidx.room.TypeConverter
import java.util.Date
import java.util.UUID

class FtConverters {
    @TypeConverter
    fun fromDateToLong(date: Date): Long = date.time

    @TypeConverter
    fun dateFromLong(time: Long): Date = Date(time)
}
