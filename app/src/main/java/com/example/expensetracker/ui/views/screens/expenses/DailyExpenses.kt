package com.example.expensetracker.ui.views.screens.expenses

import com.example.expensetracker.data.model.Expense
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.expensetracker.ui.views.components.ExpenseCard


@Composable
fun DailyExpenses(onExpenseClick: (Expense) -> Unit) {
    LazyColumn() {
        items() { expense ->
            ExpenseCard(
                expense = expense,
                onClick = { onExpenseClick(expense) }
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DailyExpensesPreview() {
//    DailyExpenses(Modifier, sampleExpenses)
//}