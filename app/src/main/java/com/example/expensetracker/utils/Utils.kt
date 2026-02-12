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

val sampleExpenses = listOf(
    Expense("Kawa", 1500L),
    Expense("Paliwo", 25000L),
    Expense("Bilet do kina", 3500L),
    Expense("Czynsz", 120000L),
    Expense("Zakupy spożywcze", 8540L),
    Expense("Internet", 6000L),
    Expense("Siłownia", 15000L),
    Expense("Obiad", 4500L),
    Expense("Prezent", 10000L),
    Expense("Subskrypcja", 2999L)
)