package com.example.expensetracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.expensetracker.data.model.Expense

@Dao
interface ExpenseDao {
    // Basic expense operations
    @Insert
    fun insertExpense(expense: Expense)
    @Update
    fun updateExpense(expense: Expense)
    @Delete
    fun deleteExpense(expense: Expense)
    // Getters
    // TODO: Add getters
}