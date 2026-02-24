package com.example.expensetracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.data.repository.ExpenseRepository
import com.example.expensetracker.ui.state.EditExpenseState
import com.example.expensetracker.ui.state.ExpenseDialogMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditExpenseViewModel(private val repository: ExpenseRepository) : ViewModel() {
    private val _expenseState = MutableStateFlow(EditExpenseState())
    val expenseState = _expenseState.asStateFlow()

    fun initExpense(expense: Expense?) {
        if (expense != null) {
            _expenseState.update {
                it.copy(
                    expenseId = expense.id,
                    name = expense.name,
                    amount = (expense.amount / 100.0).toString().replace(".", ","),
                    mode = ExpenseDialogMode.EDIT,
                    isSaved = false,
                    isDeleted = false
                )
            }
        } else {
            // Czyści formularz dla całkowicie nowego wydatku
            _expenseState.value = EditExpenseState(isSaved = false, isDeleted = false)
        }
    }

    fun resetCloseFlags() {
        _expenseState.update { it.copy(isSaved = false, isDeleted = false) }
    }

    fun onNameChange(newName: String) {
        _expenseState.update {
            it.copy(
                name = newName,
                nameError = newName.isBlank()
            )
        }
    }

    fun onAmountChange(newAmount: String) {
        val sanitizedAmount = newAmount.replace(".", ",")
        if (sanitizedAmount.count { it == ',' } <= 1 &&
            sanitizedAmount.all { it.isDigit() || it == ',' })
        {
            _expenseState.update {
                it.copy(
                    amount = sanitizedAmount,
                    amountError = sanitizedAmount.isBlank()
                )
            }
        }
    }

    fun saveExpense() {
        val currentState = _expenseState.value
        val isNameError = currentState.name.isBlank()
        val isAmountError = currentState.amount.isBlank()

        _expenseState.update {
            it.copy(nameError = isNameError, amountError = isAmountError)
        }

        if (!isNameError && !isAmountError) {
            viewModelScope.launch {
                val amountLong = try {
                    (currentState.amount.replace(',', '.').toDouble() * 100).toLong()
                } catch (_: Exception) {
                    0L
                }
                repository.upsert(Expense(currentState.name, amountLong))
                _expenseState.update { it.copy(isSaved = true) }
            }
        }
    }

    fun deleteExpense() {
        _expenseState.value.expenseId?.let { id ->
            viewModelScope.launch {
                repository.deleteById(id)
                _expenseState.update { it.copy(isDeleted = true) }
            }
        }
    }
}