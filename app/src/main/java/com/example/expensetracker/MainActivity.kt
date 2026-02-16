package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.expensetracker.data.local.ExpenseDatabase
import com.example.expensetracker.ui.screens.MainScreen
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = ExpenseDatabase.getDatabase(this).expenseDao()

        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                MainScreen(dao)
            }
        }
    }
}
