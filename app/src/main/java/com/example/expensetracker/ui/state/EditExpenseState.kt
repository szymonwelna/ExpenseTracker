package com.example.expensetracker.ui.state

data class EditExpenseState(
    var name: String = "",
    var amount: String = "",
    var nameError: Boolean = false,
    var amountError: Boolean = false,
    val date: String = "" // Temporary, until I decide on better solution
)