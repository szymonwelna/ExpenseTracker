package com.example.expensetracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.expensetracker.data.ExpenseApplication
import com.example.expensetracker.data.local.ExpensesRepository
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.ui.ExpenseScope
import com.example.expensetracker.ui.ExpensesUiState
import com.example.expensetracker.ui.SelectedScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainScreenViewModel(private val repository: ExpensesRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ExpensesUiState())
    val uiState: StateFlow<ExpensesUiState> = _uiState.asStateFlow()

    fun onAddExpenseClick() {
        _uiState.update { it.copy(isEditorOpen = true, selectedExpense = null) }
    }

    fun onExpenseClick(expense: Expense) {
        _uiState.update { it.copy(isEditorOpen = true, selectedExpense = expense) }
    }

    fun onScreenChange(screen: SelectedScreen) {
        _uiState.update { it.copy(currentScreen = screen) }
    }

    fun onScopeChange(scope: ExpenseScope) {
        _uiState.update { it.copy(currentScope = scope) }
    }

    fun onDismissDialog() {
        _uiState.update { it.copy(selectedScreen = it) }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ExpenseApplication)
                val repository = application.container.expensesRepository
                MainScreenViewModel(repository = repository)
            }
        }
    }
}