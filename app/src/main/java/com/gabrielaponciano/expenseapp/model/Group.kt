package com.gabrielaponciano.expenseapp.model

data class Group(
    val id: Int = 0,
    val name: String,
    val users: List<User> = emptyList()
)

data class GroupCreate(
    val userId: Int,
    val name: String,
    val password: String
)

data class GroupJoin(
    val userId: Int,
    val groupId: Int,
    val password: String
)

data class GroupLeave(
    val userId: Int,
    val groupId: Int
)