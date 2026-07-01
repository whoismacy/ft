package com.shrmrm.ft.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Currency

@RequiresApi(Build.VERSION_CODES.O)
fun convertFromInstant(time: Instant): String {
    val zone = ZoneId.systemDefault()
    val localDateTime = time.atZone(zone).toLocalDateTime()
    val formatter = DateTimeFormatter.ofPattern("MMM d")
    return localDateTime.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun instantStartOfTheDay(time: Instant): Instant {
    val zone = ZoneId.systemDefault()
    return time
        .atZone(zone)
        .toLocalDate()
        .atStartOfDay(zone)
        .toInstant()
}

fun formatCurrency(amount: Int): String {
    val formatter =
        NumberFormat
            .getCurrencyInstance()
    formatter.currency = Currency.getInstance("KES")
    return formatter.format(amount)
}
