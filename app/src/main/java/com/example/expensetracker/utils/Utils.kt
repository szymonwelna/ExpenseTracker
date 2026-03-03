package com.example.expensetracker.utils

import com.example.expensetracker.data.model.Expense
import java.text.NumberFormat
import java.time.Instant.ofEpochMilli
import java.time.ZoneId.systemDefault
import java.time.ZonedDateTime.ofInstant
import java.util.Locale
import kotlin.time.Instant
import kotlin.time.Instant.Companion.fromEpochMilliseconds

// Extension Function
fun Long.convertToCurrency(): String {
    val locale = Locale.Builder()
        .setLanguage("pl")
        .setRegion("PL")
        .build()
    val formatter = NumberFormat.getCurrencyInstance(locale)
    return formatter.format(this / 100.0)
}

fun Instant.trimToDate(): Instant {
    val javaInstant = ofEpochMilli(this.toEpochMilliseconds())
    val zoneId = systemDefault()
    val startOfDayMillis = ofInstant(javaInstant, zoneId)
        .toLocalDate()
        .atStartOfDay(zoneId)
        .toInstant()
        .toEpochMilli()
    return fromEpochMilliseconds(startOfDayMillis)
}