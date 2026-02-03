package com.example.expensetracker.support

import java.text.NumberFormat
import java.util.Locale

fun Long.convertToCurrency(): String {
    val locale = Locale.Builder()
        .setLanguage("pl")
        .setRegion("PL")
        .build()
    val formatter = NumberFormat.getCurrencyInstance(locale)
    return formatter.format(this / 100.0)
}