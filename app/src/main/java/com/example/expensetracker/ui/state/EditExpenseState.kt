package com.example.expensetracker.ui.state

data class EditExpenseState(
    val mode: ExpenseDialogMode = ExpenseDialogMode.ADD_TODAY,
    val expenseId: Long? = null,
    val name: String = "",
    val amount: String = "",
    val date: String = "", // Temporary, until I decide on better solution
    val nameError: Boolean = false,
    val amountError: Boolean = false,
    val dateError: Boolean = false
)

enum class ExpenseDialogMode {
    ADD_TODAY,
    ADD_WITH_DATE,
    EDIT
}