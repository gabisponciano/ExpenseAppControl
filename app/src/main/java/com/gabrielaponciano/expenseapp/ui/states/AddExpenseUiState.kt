package com.gabrielaponciano.expenseapp.ui.states

data class AddExpenseUiState(
    val name: String = "",
    val date: Long? = null,
    val value: Float? = null,
)