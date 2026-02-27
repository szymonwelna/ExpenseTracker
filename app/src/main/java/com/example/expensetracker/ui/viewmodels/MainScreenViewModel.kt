package com.example.expensetracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.data.repository.ExpenseRepository
import com.example.expensetracker.ui.state.ExpenseScope
import com.example.expensetracker.ui.state.MainScreenState
import com.example.expensetracker.ui.state.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainScreenViewModel(private val repository: ExpenseRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState = _uiState.asStateFlow()

    fun onAddExpenseClick() {
        _uiState.update { it.copy(showEditExpense = true, selectedExpense = null) }
    }

    val expenses = combine(
        repository.getAll(),
        _uiState
    ) { allExpenses, state ->
        when (state.scope) {
            ExpenseScope.Daily -> {
                val today = java.time.LocalDate.now().toString()
                allExpenses.filter { /* TODO: dodać logikę sprawdzającą czy wydatek ma datę ustawioną na dzisiaj */ true }
            }
            ExpenseScope.Weekly -> {
                allExpenses
            }
            ExpenseScope.Monthly -> {
                allExpenses
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onScreenChange(newScreen: Screens) {
        _uiState.update { it.copy(screen = newScreen) }
    }

    fun onToggleEditDialog(show: Boolean) {
        _uiState.update {
            it.copy(
                showEditExpense = show,
                selectedExpense = if (!show) null else it.selectedExpense
            )
        }
    }

    fun onExpenseClick(expense: Expense) {
        _uiState.update {
            it.copy(
                selectedExpense = expense,
                showEditExpense = true
            )
        }
    }
}