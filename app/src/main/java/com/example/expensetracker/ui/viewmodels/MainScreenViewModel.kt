package com.example.expensetracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.local.ExpenseDao
import com.example.expensetracker.ui.state.MainScreenState
import com.example.expensetracker.ui.state.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainScreenViewModel(private val dao: ExpenseDao) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState = _uiState.asStateFlow()

    val expenses = dao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onScreenChange(newScreen: Screens) {
        _uiState.update { it.copy(screen = newScreen) }
    }

    fun onToggleEditDialog(show: Boolean) {
        _uiState.update { it.copy(showEditExpense = show) }
    }
}