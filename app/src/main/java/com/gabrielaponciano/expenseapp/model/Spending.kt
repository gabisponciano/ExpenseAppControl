package com.gabrielaponciano.expenseapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Spending(
    val id: Int = 0,
    val name: String,
    val day: String,
    var value: Float,
    val userId: Int
)

@RequiresApi(Build.VERSION_CODES.O)
fun createSpending(
    name: String,
    value: Float,
    userId: Int,
    day: LocalDateTime
): Spending {
    // Formatando o LocalDateTime para ISO 8601
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val formattedDate = day.format(formatter)

    return Spending(
        name = name,
        value = value,
        userId = userId,
        day = formattedDate
    )
}