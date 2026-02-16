package com.example.expensetracker.utils

import com.example.expensetracker.data.model.Expense
import java.text.NumberFormat
import java.util.Locale

// Extension Function
fun Long.convertToCurrency(): String {
    val locale = Locale.Builder()
        .setLanguage("pl")
        .setRegion("PL")
        .build()
    val formatter = NumberFormat.getCurrencyInstance(locale)
    return formatter.format(this / 100.0)
}