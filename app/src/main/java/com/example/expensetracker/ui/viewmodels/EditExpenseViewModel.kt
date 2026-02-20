package com.example.expensetracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.local.ExpenseDao
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.ui.state.EditExpenseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditExpenseViewModel(private val dao: ExpenseDao) : ViewModel() {

    private val _expenseState = MutableStateFlow(EditExpenseState())
    val expenseState = _expenseState.asStateFlow()

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

    fun saveExpense(onSuccess: () -> Unit) {
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
                } catch (e: Exception) {
                    0L
                }
                dao.insert(Expense(currentState.name, amountLong))
                onSuccess()
            }
        }
    }
}