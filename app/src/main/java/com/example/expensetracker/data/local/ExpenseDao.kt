package com.example.expensetracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.expensetracker.data.model.Expense

@Dao
interface ExpenseDao {
    // Setters
    @Insert
    fun insertExpense(expense: Expense)
    @Update
    fun updateExpense(expense: Expense)
    @Delete
    fun deleteExpense(expense: Expense)
    // Getters
    // TODO: Add getters

    // Support
    @Query("SELECT EXISTS(SELECT 1 FROM expenses WHERE id = :id)")
    suspend fun exists(id: Long): Boolean
}