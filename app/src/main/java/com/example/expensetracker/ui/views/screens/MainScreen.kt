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
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.expensetracker.ui.views.components.EditExpense
import com.example.expensetracker.ui.views.screens.expenses.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.R
import com.example.expensetracker.ui.ExpenseScope
import com.example.expensetracker.ui.viewmodels.MainScreenViewModel

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
        containerColor =  barsColor,
        topBar = {
            TopAppBar(
                title = { /*TODO: Dodać wyświetlanie aktualnego zakresu (dzień/tydzien itp.)*/ },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = barsColor,
                    titleContentColor = MaterialTheme.colorScheme.onSurface)
                )
        },
        bottomBar = {
            NavigationBar {}
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
            when (state.currentScope) {
                ExpenseScope.Daily -> DailyExpenses()
                ExpenseScope.Weekly -> WeeklyExpenses()
                ExpenseScope.Monthly -> MonthlyExpenses()
            }
        }

        if (state.showEditExpense) {
            EditExpense() {}
        }
    }
}