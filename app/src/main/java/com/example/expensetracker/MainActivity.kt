package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.expensetracker.data.ExpenseApplication
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import com.example.expensetracker.ui.views.screens.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val container = (application as ExpenseApplication).container
        val expensesRepository = container.expensesRepository

        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                MainScreen()
            }
        }
    }
}