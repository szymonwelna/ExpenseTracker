package com.example.expensetracker.ui.views.screens.expenses

import com.example.expensetracker.data.model.Expense
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.ui.viewmodels.DailyExpensesViewModel
import com.example.expensetracker.ui.views.components.ExpenseCard


@Composable
fun DailyExpenses(
    viewModel: DailyExpensesViewModel = viewModel(factory = DailyExpensesViewModel.Factory),
    onExpenseClick: (Expense) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LazyColumn {
        items(state.expenses) { expense ->
            ExpenseCard(
                expense = expense,
                onClick = { onExpenseClick(expense) }
            )
        }
    }
}