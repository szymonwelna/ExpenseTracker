package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.expensetracker.data.local.ExpenseDatabase
import com.example.expensetracker.data.repository.ExpenseRepository
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import com.example.expensetracker.ui.views.screens.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = ExpenseDatabase.getDatabase(this)
        val dao = database.expenseDao()
        val repository = ExpenseRepository(dao)

        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                MainScreen(repository = repository)
            }
        }
    }
}
