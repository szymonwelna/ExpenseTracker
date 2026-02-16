package com.example.expensetracker.ui.components

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
import kotlinx.coroutines.launch

@Composable
fun AddNewExpense(dao: ExpenseDao, onDismiss: () -> Unit) {
    var expenseName by remember { mutableStateOf("") }
    var expenseAmount by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(false) }
    var amountError by remember { mutableStateOf(false) }

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
                value = expenseName,
                onValueChange = {
                    expenseName = it
                    if (it.isNotBlank()) nameError = false
                },
                label = { Text("Nazwa") },
                isError = nameError,
                singleLine = true,
                modifier = Modifier.weight(0.6f)
            )

            OutlinedTextField(
                value = expenseAmount,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        expenseAmount = it
                        if (it.isNotBlank()) amountError = false
                    }
                },
                label = { Text("Kwota") },
                suffix = { Text("zł") },
                isError = amountError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.weight(0.4f)
            )
        }

        if (nameError || amountError) {
            Text(
                text = "Proszę uzupełnić wymagane pola",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Button(
            onClick = {
                nameError = expenseName.isBlank()
                amountError = expenseAmount.isBlank()

                if (!nameError && !amountError) {
                    coroutineScope.launch {
                        val amount = expenseAmount.toLongOrNull() ?: 0L
                        dao.insert(Expense(expenseName, amount))
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