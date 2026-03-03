package com.example.expensetracker.ui

import com.example.expensetracker.data.model.Expense
import kotlin.time.Clock
import kotlin.time.Instant

enum class SelectedScreen() { Expenses, Summary, Settings }

enum class ExpenseScope() { Daily, Weekly, Monthly }

data class ExpensesUiState(
    val startDate: Instant = Clock.System.now(),
    val endDate: Instant = Clock.System.now(),
    val currentScreen: SelectedScreen = SelectedScreen.Expenses,
    val currentScope: ExpenseScope = ExpenseScope.Daily, // Daily as default view
    val expenses: List<Expense> = emptyList(), // List of expenses
    val isEditorOpen: Boolean = false, // Flag to show/hide edit dialog
    val selectedExpense: Expense? = null // If no expense is selected, new will be added
)