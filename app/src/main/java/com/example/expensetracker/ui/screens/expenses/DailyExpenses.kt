package com.example.expensetracker.ui.screens.expenses

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
import com.example.expensetracker.support.convertToCurrency
import com.example.expensetracker.support.sampleExpenses


@Composable
fun DailyExpenses(modifier: Modifier = Modifier, expensesList: List<Expense> = emptyList()) {
    LazyColumn(modifier = modifier) {
        items(expensesList) { expense ->
            ExpenseCard(expense)
        }
    }
}

@Composable
fun ExpenseCard(expense: Expense) {
    Button(
        onClick = { /* TODO */ },
        elevation = ButtonDefaults.buttonElevation(4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray,
            contentColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = expense.name, fontWeight = Bold)
            Text(text = expense.amount.convertToCurrency())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DailyExpensesPreview() {
    DailyExpenses(Modifier, sampleExpenses)
}