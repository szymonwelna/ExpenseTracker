package com.example.expensetracker.ui.views.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.R
import com.example.expensetracker.ui.ExpenseScope
import com.example.expensetracker.ui.SelectedScreen
import com.example.expensetracker.ui.viewmodels.MainScreenEvent
import com.example.expensetracker.ui.viewmodels.MainScreenViewModel
import com.example.expensetracker.ui.views.components.EditExpense
import com.example.expensetracker.ui.views.screens.expenses.DailyExpenses
import com.example.expensetracker.ui.views.screens.expenses.MonthlyExpenses
import com.example.expensetracker.ui.views.screens.expenses.WeeklyExpenses

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = viewModel(factory = MainScreenViewModel.Factory)
) {
    val state by viewModel.uiState.collectAsState()

    val barsColor = MaterialTheme.colorScheme.surfaceContainer
    val contentColor = MaterialTheme.colorScheme.background

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = barsColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when(state.currentScreen) {
                            SelectedScreen.Expenses -> "Wydatki: ${state.currentScope.name}"
                            SelectedScreen.Summary -> stringResource(R.string.summary_screen_label)
                            SelectedScreen.Settings -> stringResource(R.string.settings_screen_label)
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = barsColor,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = barsColor) {
                val screens = SelectedScreen.entries.toTypedArray()
                screens.forEach { screen ->
                    NavigationBarItem(
                        selected = state.currentScreen == screen,
                        onClick = { viewModel.onEvent(MainScreenEvent.ScreenChange(screen)) },
                        label = { Text(screen.name) },
                        icon = {
                            val icon = when(screen) {
                                SelectedScreen.Expenses -> Icons.Default.Home
                                SelectedScreen.Summary -> Icons.Default.PieChart
                                SelectedScreen.Settings -> Icons.Default.Settings
                            }
                            Icon(icon, contentDescription = null)
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            if (state.currentScreen == SelectedScreen.Expenses) {
                FloatingActionButton(
                    onClick = { viewModel.onEvent(MainScreenEvent.AddExpenseClick) },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = stringResource(R.string.new_expense_icon_description)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(color = contentColor)
                .innerShadow(shape = AbsoluteRoundedCornerShape(size = 16.dp), Shadow(radius = 12.dp))
        ) {
            when (state.currentScreen) {
                SelectedScreen.Expenses -> {
                    when (state.currentScope) {
                        ExpenseScope.Daily -> DailyExpenses(
                            onExpenseClick = { expense -> viewModel.onEvent(MainScreenEvent.ExpenseClick(expense)) }
                        )
                        ExpenseScope.Weekly -> WeeklyExpenses()
                        ExpenseScope.Monthly -> MonthlyExpenses()
                    }
                }
                SelectedScreen.Summary -> { /* SummaryScreen() */ }
                SelectedScreen.Settings -> { /* SettingsScreen() */ }
            }
        }

        if (state.isEditorOpen) {
            EditExpense(
                repository = viewModel.repository, // Uwaga: EditExpense ma swój VM, to jest OK, ale można to uprościć
                expenseToEdit = state.selectedExpense,
                onDismiss = { viewModel.onEvent(MainScreenEvent.DismissDialog) }
            )
        }
    }
}