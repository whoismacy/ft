package com.shrmrm.ft.data.db

import androidx.room.TypeConverter
import kotlin.time.Instant

class FtConverters {
    @TypeConverter
    fun fromInstantToLong(instant: Instant): Long = instant.toEpochMilliseconds()

    @TypeConverter
    fun instantFromLong(time: Long): Instant = Instant.fromEpochMilliseconds(time)
}
