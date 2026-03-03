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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.R
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.ui.state.ExpenseDialogMode
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExpense(
    repository: ExpenseRepository,
    expenseToEdit: Expense? = null,
    onDismiss: () -> Unit
) {
    val editViewModel = viewModel<EditExpenseViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return EditExpenseViewModel(repository) as T
            }
        }
    )

    val expenseState by editViewModel.expenseState.collectAsState()

    LaunchedEffect(expenseToEdit) {
        editViewModel.initExpense(expenseToEdit)
    }

    LaunchedEffect(Unit) {
        editViewModel.uiEvent.collectLatest { event ->
            when (event) {
                is EditExpenseViewModel.UiEvent.CloseDialog -> onDismiss()
            }
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
                    text = stringResource(R.string.new_expense_title),
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
                        label = { Text(stringResource(R.string.ee_expense_name_label)) },
                        isError = expenseState.nameError,
                        singleLine = true,
                        modifier = Modifier.weight(0.6f)
                    )

                    OutlinedTextField(
                        value = expenseState.amount,
                        onValueChange = { editViewModel.onAmountChange(it) },
                        label = { Text(stringResource(R.string.ee_expense_value_label)) },
                        suffix = { Text("zł") }, // TODO: add currency
                        isError = expenseState.amountError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier.weight(0.4f)
                    )
                }

                if (expenseState.mode != ExpenseDialogMode.ADD_TODAY) {
                    // TODO Add date picker
                }


                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (expenseState.mode == ExpenseDialogMode.EDIT) {
                        Button(
                            onClick = { editViewModel.deleteExpense() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(stringResource(R.string.delete_expense_button))
                        }
                    }

                    Button(
                        onClick = { editViewModel.saveExpense() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.add_expense_button))
                    }
                }
            }
        }
    }
}