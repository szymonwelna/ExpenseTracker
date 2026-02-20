package com.example.expensetracker.ui.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class MainScreenState(
    val showEditExpense: Boolean = false,
    val screen: Screens = Screens.ExpensesScreen,
    val scope: ExpenseScope = ExpenseScope.Daily
) {
    val currentTitle: String
        get() = if (screen == Screens.ExpensesScreen) scope.label else screen.label
}

enum class ExpenseScope(val label: String, val icon: ImageVector) {
    Daily("Dzienny", Icons.Default.DateRange),
    Weekly("Tygodniowy", Icons.Default.DateRange),
    Monthly("Miesięczny", Icons.Default.DateRange)
}

enum class Screens(val label: String, val icon: ImageVector) {
    Settings("Ustawienia", Icons.Default.Settings),
    ExpensesScreen("Wydatki", Icons.Default.Home),
    SummaryScreen("Podsumowanie", Icons.Default.Star)
}