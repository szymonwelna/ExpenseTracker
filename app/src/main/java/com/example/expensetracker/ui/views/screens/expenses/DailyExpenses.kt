package com.example.expensetracker.ui.views.screens.expenses

import com.example.expensetracker.data.model.Expense
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expensetracker.ui.views.components.ExpenseCard
import com.example.expensetracker.utils.convertToCurrency
import com.example.expensetracker.utils.sampleExpenses


@Composable
fun DailyExpenses(modifier: Modifier = Modifier, expensesList: List<Expense> = emptyList()) {
    LazyColumn(modifier = modifier) {
        items(expensesList) { expense ->
            ExpenseCard(expense)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DailyExpensesPreview() {
    DailyExpenses(Modifier, sampleExpenses)
}