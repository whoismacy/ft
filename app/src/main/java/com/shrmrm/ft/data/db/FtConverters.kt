package com.shrmrm.ft.data.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
class FtConverters {
    @TypeConverter
    fun fromInstantToLong(instant: Instant): Long = instant.toEpochMilli()

    @TypeConverter
    fun instantFromLong(time: Long): Instant = Instant.ofEpochMilli(time)
}
