package com.example.expensetracker.ui.state

data class EditExpenseState(
    val expenseId: Long? = null,
    val name: String = "",
    val amount: String = "",
    val nameError: Boolean = false,
    val amountError: Boolean = false,
    val mode: ExpenseDialogMode = ExpenseDialogMode.ADD_TODAY,
    val date: String = "", // Temporary, until I decide on better solution
    val dateError: Boolean = false,
    val isSaved: Boolean = false,
    val isDeleted: Boolean = true
)

enum class ExpenseDialogMode {
    ADD_TODAY,
    ADD_WITH_DATE,
    EDIT
}