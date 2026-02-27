package com.example.expensetracker.ui.views.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.expensetracker.ui.state.ExpenseScope
import com.example.expensetracker.ui.state.Screens
import com.example.expensetracker.ui.viewmodels.MainScreenViewModel
import com.example.expensetracker.ui.views.components.EditExpense
import com.example.expensetracker.ui.views.screens.expenses.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.R
import com.example.expensetracker.data.repository.ExpenseRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(repository: ExpenseRepository, modifier: Modifier = Modifier) {
    val barsColor = MaterialTheme.colorScheme.surfaceContainer
    val contentColor = MaterialTheme.colorScheme.background

    val viewModel: MainScreenViewModel = viewModel {
        MainScreenViewModel(repository)
    }

    val state by viewModel.uiState.collectAsState()
    val expenses by viewModel.expenses.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor =  barsColor,
        topBar = {
            TopAppBar(
                title = { Text(state.currentTitle) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = barsColor,
                    titleContentColor = MaterialTheme.colorScheme.onSurface)
                )
        },
        bottomBar = {
            NavigationBar {
                Screens.entries.forEach { navItem ->
                    NavigationBarItem(
                        selected = state.screen == navItem,
                        onClick = { viewModel.onScreenChange(navItem) },
                        label = { Text(navItem.label) },
                        icon = { Icon(navItem.icon, contentDescription = navItem.label) }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAddExpenseClick() },
                shape = Shapes().medium
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.new_expense_icon_description)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(size = 16.dp))
                .background(color = contentColor)
                .innerShadow(shape = AbsoluteRoundedCornerShape(size = 16.dp), Shadow(radius = 12.dp))
        ) {
            when (state.scope) {
                ExpenseScope.Daily -> DailyExpenses(
                    modifier = Modifier,
                    expensesList = expenses,
                    onExpenseClick = { viewModel.onExpenseClick(it) }
                )
                ExpenseScope.Weekly -> WeeklyExpenses(Modifier, expenses)
                ExpenseScope.Monthly -> MonthlyExpenses(Modifier, expenses)
            }
        }

        if (state.showEditExpense) {
            EditExpense(
                repository = repository,
                expenseToEdit = state.selectedExpense
            ) {
                viewModel.onToggleEditDialog(false)
            }
        }
    }
}