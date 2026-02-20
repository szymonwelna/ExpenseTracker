package com.example.expensetracker.ui.views.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState // Dodany import
import androidx.compose.runtime.getValue // Dodany import
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.expensetracker.ui.viewmodels.EditExpenseViewModel

@Composable
fun EditExpense(viewModel: EditExpenseViewModel, onDismiss: () -> Unit) {
    val expenseState by viewModel.expenseState.collectAsState()

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
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Nazwa") },
                isError = expenseState.nameError,
                singleLine = true,
                modifier = Modifier.weight(0.6f)
            )

            OutlinedTextField(
                value = expenseState.amount,
                onValueChange = { viewModel.onAmountChange(it) },
                label = { Text("Kwota") },
                suffix = { Text("zł") },
                isError = expenseState.amountError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.weight(0.4f)
            )
        }

        Button(
            onClick = { viewModel.saveExpense { onDismiss() } },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Dodaj wydatek")
        }
    }
}