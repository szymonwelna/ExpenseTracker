package com.example.expensetracker.ui.views.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.data.local.ExpenseDao
import com.example.expensetracker.ui.state.ExpenseScope
import com.example.expensetracker.ui.state.Screens
import com.example.expensetracker.ui.viewmodels.EditExpenseViewmodel
import com.example.expensetracker.ui.viewmodels.MainScreenViewmodel
import com.example.expensetracker.ui.views.components.EditExpense
import com.example.expensetracker.ui.views.screens.expenses.*
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(dao: ExpenseDao, modifier: Modifier = Modifier) {
    val viewModel: MainScreenViewmodel = viewModel {
        MainScreenViewmodel(dao)
    }

    val state by viewModel.uiState.collectAsState()
    val expenses by viewModel.expenses.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(state.currentTitle) })
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
                onClick = { viewModel.onToggleEditDialog(true) },
                shape = Shapes().medium
            ) {
                Icon(Icons.Default.Add, contentDescription = "Dodaj wydatek")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (state.screen) {
                Screens.Settings -> Text("Ekran Ustawień")
                Screens.ExpensesScreen -> {
                    when (state.scope) {
                        ExpenseScope.Daily -> DailyExpenses(Modifier, expenses)
                        ExpenseScope.Weekly -> WeeklyExpenses(Modifier, expenses)
                        ExpenseScope.Monthly -> MonthlyExpenses(Modifier, expenses)
                    }
                }
                Screens.SummaryScreen -> Text("Ekran Podsumowania")
            }
        }

        if (state.showEditExpense) {
            BasicAlertDialog(
                onDismissRequest = { viewModel.onToggleEditDialog(false) }
            ) {
                Surface(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    shape = MaterialTheme.shapes.large,
                    tonalElevation = AlertDialogDefaults.TonalElevation
                ) {
                    val editViewModel = viewModel<EditExpenseViewmodel>(
                        factory = object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return EditExpenseViewmodel(dao) as T
                            }
                        }
                    )

                    EditExpense(
                        viewModel = editViewModel,
                        onDismiss = { viewModel.onToggleEditDialog(false) }
                    )
                }
            }
        }
    }
}