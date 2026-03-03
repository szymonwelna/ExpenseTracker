package com.example.expensetracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.expensetracker.data.ExpenseApplication
import com.example.expensetracker.data.local.ExpensesRepository
import com.example.expensetracker.ui.ExpensesUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DailyExpensesViewModel(private val repository: ExpensesRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ExpensesUiState())
    val uiState: StateFlow<ExpensesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.map { it.startDate }
                .distinctUntilChanged()
                .flatMapLatest { date ->
                    repository.getDailyExpenses(date)
                }
                .collect { expensesList ->
                    _uiState.update { it.copy(expenses = expensesList) }
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ExpenseApplication)
                DailyExpensesViewModel(repository = application.container.expensesRepository)
            }
        }
    }
}