package com.example.expensetracker.ui.views.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.data.repository.ExpenseRepository
import com.example.expensetracker.ui.viewmodels.EditExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExpense(repository: ExpenseRepository, onDismiss: () -> Unit) {
    val editViewModel = viewModel<EditExpenseViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return EditExpenseViewModel(repository) as T
            }
        }
    )

    val expenseState by editViewModel.expenseState.collectAsState()

    LaunchedEffect(expenseState.isSaved) {
        if (expenseState.isSaved) {
            onDismiss()
        }
    }

    BasicAlertDialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Nowy wydatek",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = expenseState.name,
                        onValueChange = { editViewModel.onNameChange(it) },
                        label = { Text("Nazwa") },
                        isError = expenseState.nameError,
                        singleLine = true,
                        modifier = Modifier.weight(0.6f)
                    )

                    OutlinedTextField(
                        value = expenseState.amount,
                        onValueChange = { editViewModel.onAmountChange(it) },
                        label = { Text("Kwota") },
                        suffix = { Text("zł") },
                        isError = expenseState.amountError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier.weight(0.4f)
                    )
                }

                Button(
                    onClick = { editViewModel.saveExpense() },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                ) {
                    Text("Dodaj wydatek")
                }
            }
        }
    }
}