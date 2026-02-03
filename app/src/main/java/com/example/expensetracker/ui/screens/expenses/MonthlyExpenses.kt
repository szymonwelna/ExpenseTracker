package com.example.expensetracker.ui.screens.expenses

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.expensetracker.data.model.Expense

@Composable
fun MonthlyExpenses(modifier: Modifier, expensesList: List<Expense>) {
    Text("Ekran wydatków miesięcznych")
}