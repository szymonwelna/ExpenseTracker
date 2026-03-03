package com.example.expensetracker.ui.viewmodels

import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.ui.ExpenseScope
import com.example.expensetracker.ui.SelectedScreen

sealed interface MainScreenEvent {
    data object AddExpenseClick : MainScreenEvent
    data class ExpenseClick(val expense: Expense) : MainScreenEvent
    data class ScreenChange(val screen: SelectedScreen) : MainScreenEvent
    data class ScopeChange(val scope: ExpenseScope) : MainScreenEvent
    data object DismissDialog : MainScreenEvent
}