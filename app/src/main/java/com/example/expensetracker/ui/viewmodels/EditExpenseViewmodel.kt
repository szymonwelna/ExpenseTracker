package com.example.expensetracker.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.local.ExpenseDao
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.ui.state.EditExpenseState
import kotlinx.coroutines.launch

class EditExpenseViewmodel(private val dao: ExpenseDao) : ViewModel() {

    var expenseState by mutableStateOf(EditExpenseState())
        private set

    fun onNameChange(newName: String) {
        expenseState = expenseState.copy(
            name = newName,
            nameError = newName.isBlank()
        )
    }

    fun onAmountChange(newAmount: String) {
        val sanitizedAmount = newAmount.replace(".", ",")
        if (sanitizedAmount.count { it == ',' } <= 1 &&
            sanitizedAmount.all { it.isDigit() || it == ',' })
        {
            expenseState = expenseState.copy(
                amount = sanitizedAmount,
                amountError = sanitizedAmount.isBlank()
            )
        }
    }

    fun saveExpense(onSuccess: () -> Unit) {
        val isNameError = expenseState.name.isBlank()
        val isAmountError = expenseState.amount.isBlank()

        expenseState = expenseState.copy(nameError = isNameError, amountError = isAmountError)
        if (!isNameError && !isAmountError) {
            viewModelScope.launch {
                val amountLong = try {
                    (expenseState.amount.replace(',', '.').toDouble() * 100).toLong()
                } catch (e: Exception) { 0L }

                dao.insert(Expense(expenseState.name, amountLong))
                onSuccess()
            }
        }
    }
}