package com.example.expensetracker.ui.state

data class EditExpenseState(
    val name: String = "",
    val amount: String = "",
    val nameError: Boolean = false,
    val amountError: Boolean = false,
    val date: String = "", // Temporary, until I decide on better solution
    val isSaved: Boolean = false
)