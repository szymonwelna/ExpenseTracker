package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.ExpenseDao
import com.example.expensetracker.data.model.Expense
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    fun getAll(): Flow<List<Expense>> {
        return expenseDao.getAll()
    }

    fun getById(id: Long): Flow<Expense> {
        return expenseDao.getById(id)
    }

    suspend fun upsert(expense: Expense) {
        expenseDao.upsert(expense)
    }

    suspend fun delete(expense: Expense) {
        expenseDao.delete(expense)
    }

    suspend fun deleteById(id: Long) {
        expenseDao.deleteById(id)
    }
}