package com.gabrielaponciano.expenseapp.model

data class BodyResponse<T>(
    val message: String,
    val data: T
)