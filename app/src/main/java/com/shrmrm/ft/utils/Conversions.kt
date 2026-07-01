package com.shrmrm.ft.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Currency
import java.util.Locale

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
    val formatter = NumberFormat.getCurrencyInstance() as DecimalFormat
    formatter.currency = Currency.getInstance("KES")
    formatter.maximumFractionDigits = 0
    formatter.minimumFractionDigits = 0
    val symbols = formatter.decimalFormatSymbols
    symbols.currencySymbol = " KShs "
    formatter.decimalFormatSymbols = symbols
    return formatter.format(amount)
}
