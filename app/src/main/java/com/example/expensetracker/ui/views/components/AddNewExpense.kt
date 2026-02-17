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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.expensetracker.data.local.ExpenseDao
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.ui.state.EditExpenseState
import kotlinx.coroutines.launch

@Composable
fun AddNewExpense(dao: ExpenseDao, onDismiss: () -> Unit) {
    var expenseState by remember { mutableStateOf(EditExpenseState()) }
    val coroutineScope = rememberCoroutineScope()

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
                onValueChange = {
                    expenseState.name = it
                    if (it.isNotBlank()) expenseState.nameError = false
                },
                label = { Text("Nazwa") },
                isError = expenseState.nameError,
                singleLine = true,
                modifier = Modifier.weight(0.6f)
            )

            OutlinedTextField(
                value = expenseState.amount,
                onValueChange = { input ->
                    val sanitized = input.replace('.', ',')
                    if (sanitized.count { it == ',' } <= 1 && sanitized.all { it.isDigit() || it == ',' }) {
                        expenseState.amount = sanitized
                        if (sanitized.isNotBlank()) expenseState.amountError = false
                    }
                },
                label = { Text("Kwota") },
                suffix = { Text("zł") },
                isError = expenseState.amountError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.weight(0.4f)
            )
        }

        Button(
            onClick = {
                expenseState.nameError = expenseState.name.isBlank()
                expenseState.amountError = expenseState.amount.isBlank()

                if (!expenseState.nameError && !expenseState.amountError) {
                    coroutineScope.launch {
                        val amount = try {
                            val normalized = expenseState.amount.replace(',', '.')
                            (normalized.toDouble() * 100).toLong()
                        } catch (e: NumberFormatException) {
                            0L
                        }

                        dao.insert(Expense(expenseState.name, amount))
                        onDismiss()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Dodaj wydatek")
        }
    }
}