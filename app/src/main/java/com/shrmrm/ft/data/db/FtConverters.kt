package com.shrmrm.ft.data.db

import androidx.room.TypeConverter
import java.util.Date

class FtConverters {
    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    fun fromLong(time: Long): Date = Date(time)
}
