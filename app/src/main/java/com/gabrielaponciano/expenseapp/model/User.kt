package com.gabrielaponciano.expenseapp.model

data class User(
    val id: Int? = null,
    val name: String,
    val email: String,
    val password: String = "",
    val spendings: List<Spending> = emptyList(),
    val groupId: Int? = null,
    val totalSpendings: Float? = null
)