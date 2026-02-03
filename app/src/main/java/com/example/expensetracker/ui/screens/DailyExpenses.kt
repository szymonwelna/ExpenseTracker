package com.example.expensetracker.ui.screens

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expensetracker.support.convertToCurrency


@Composable
fun DailyExpenses(modifier: Modifier = Modifier, expensesList: List<Expense> = emptyList()) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(expensesList) { expense ->
            ExpenseCard(expense)
        }
    }
}

@Composable
fun ExpenseCard(expense: Expense) {
    Button(
        onClick = { /* TODO */ },
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray,
            contentColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = expense.name)
            Text(text = expense.amount.convertToCurrency())
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ExpenseCardPreview() {
//    val expense = Expense("Kawa", 1500L)
//    ExpenseCard(expense)
//}

@Preview(showBackground = true)
@Composable
fun DailyExpensesPreview() {
    DailyExpenses(Modifier, sampleExpenses)
}

val sampleExpenses = listOf(
    Expense("Kawa", 1500L),
    Expense("Paliwo", 25000L),
    Expense("Bilet do kina", 3500L),
    Expense("Czynsz", 120000L),
    Expense("Zakupy spożywcze", 8540L),
    Expense("Internet", 6000L),
    Expense("Siłownia", 15000L),
    Expense("Obiad", 4500L),
    Expense("Prezent", 10000L),
    Expense("Subskrypcja", 2999L)
)