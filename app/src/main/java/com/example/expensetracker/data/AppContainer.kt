package com.example.expensetracker.data

import android.app.Application
import android.content.Context
import com.example.expensetracker.data.local.AppDatabase
import com.example.expensetracker.data.local.ExpensesRepository

class AppContainer(private val context: Context) {
    private val database: AppDatabase by lazy {
        AppDatabase.getDatabase(context)
    }

    val expensesRepository: ExpensesRepository by lazy {
        ExpensesRepository(database.expenseDao())
    }
}

class ExpenseApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}