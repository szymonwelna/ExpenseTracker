package com.example.expensetracker.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.expensetracker.support.sampleExpenses
import com.example.expensetracker.ui.screens.expenses.DailyExpenses

// Możliwe do wyświetlenia ekrany
enum class Screens {
    Daily,
    Weekly,
    Monthly,
    Summary
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var selectedScreen by remember { mutableStateOf(Screens.Daily) }
    val expenses = sampleExpenses // TODO -> Turn this into getting expenses from the database

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        bottomBar = { BottomAppBar {
            // TODO -> Dodać wybór okna
        } },
        floatingActionButton = {
            // TODO -> Dodać przycisk do dodawania wydatków
        }
    ) { innerPadding ->
        when(selectedScreen) {
            Screens.Daily -> DailyExpenses(innerPadding as Modifier, expenses)
//            Screens.Weekly -> WeeklyExpenses(innerPadding)
//            Screens.Monthly -> MonthlyExpenses(innerPadding)
//            Screens.Summary -> Summary(innerPadding)
            else -> DailyExpenses(innerPadding as Modifier, expenses)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    MainScreen()
}