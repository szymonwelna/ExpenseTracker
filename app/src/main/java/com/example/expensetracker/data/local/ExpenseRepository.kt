package com.example.expensetracker.data.local

import com.example.expensetracker.data.model.Expense
import kotlinx.coroutines.flow.Flow

class ExpensesRepository(private val expenseDao: ExpenseDao) {
    // Setters
    suspend fun insert(expense: Expense) {
        expenseDao.insertExpense(expense)
    }
    suspend fun update(expense: Expense) {
        expenseDao.updateExpense(expense)
    }
    suspend fun delete(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }
    // Getters
}