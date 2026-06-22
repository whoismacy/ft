package com.shrmrm.ft.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun convertFromInstant(time: Instant): String {
    val zone = ZoneId.systemDefault()
    val localDateTime = time.atZone(zone).toLocalDateTime()
    val formatter = DateTimeFormatter.ofPattern("MMM d")
    return localDateTime.format(formatter)
}
