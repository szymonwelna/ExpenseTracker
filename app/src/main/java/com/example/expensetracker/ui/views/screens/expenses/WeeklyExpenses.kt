package com.example.expensetracker.ui.views.screens.expenses

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.expensetracker.data.model.Expense

@Composable
fun WeeklyExpenses(modifier: Modifier, expensesList: List<Expense>) {
    Text("Ekran wydatków tygodniowych")
}