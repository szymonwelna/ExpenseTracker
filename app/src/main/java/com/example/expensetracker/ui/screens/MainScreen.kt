package com.example.expensetracker.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.expensetracker.support.sampleExpenses
import com.example.expensetracker.ui.screens.expenses.DailyExpenses
import com.example.expensetracker.ui.screens.expenses.MonthlyExpenses
import com.example.expensetracker.ui.screens.expenses.WeeklyExpenses

// Displayable Screens
enum class ExpenseScope(val label: String, val icon: ImageVector) {
    Daily("Dzienny", Icons.Default.DateRange),
    Weekly("Tygodniowy", Icons.Default.DateRange),
    Monthly("Miesięczny", Icons.Default.DateRange)
}

// Navigation Buttons
enum class Screens(val label: String, val icon: ImageVector) {
    Settings("Ustawienia", Icons.Default.Settings),
    ExpensesScreen("Wydatki", Icons.Default.Home),
    SummaryScreen("Podsumowanie", Icons.Default.Star)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var selectedScreen by remember { mutableStateOf(Screens.ExpensesScreen) }
    var selectedScope by remember { mutableStateOf(ExpenseScope.Daily) }
    val expenses = sampleExpenses

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(getCurrentTitle(selectedScreen, selectedScope)) })
        },
        bottomBar = {
            NavigationBar {
                Screens.entries.forEach { navItem ->
                    NavigationBarItem(
                        selected = selectedScreen == navItem,
                        onClick = { selectedScreen = navItem },
                        label = { Text(navItem.label) },
                        icon = { Icon(navItem.icon, contentDescription = navItem.label) }
                    )
                }
            }
        },
        floatingActionButton = {
            // TODO -> Dodać przycisk do dodawania wydatków
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedScreen) {
                Screens.Settings -> Text("Ekran Ustawień")
                Screens.ExpensesScreen -> {
                    when (selectedScope) {
                        ExpenseScope.Daily -> DailyExpenses(Modifier, expenses)
                        ExpenseScope.Weekly -> WeeklyExpenses(Modifier, expenses)
                        ExpenseScope.Monthly -> MonthlyExpenses(Modifier, expenses)
                    }
                }
                Screens.SummaryScreen -> Text("Ekran Podsumowania")
            }
        }
    }
}

// Utility functions
fun getCurrentTitle(screen: Screens, scope: ExpenseScope): String {
    when (screen) {
        Screens.Settings -> return "Ustawienia"
        Screens.SummaryScreen -> return "Podsumowanie"
        Screens.ExpensesScreen -> return scope.label
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    MainScreen()
}