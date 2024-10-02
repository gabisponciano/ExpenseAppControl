package com.gabrielaponciano.expenseapp.ui.viewModel

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielaponciano.expenseapp.model.Spending
import com.gabrielaponciano.expenseapp.model.createSpending
import com.gabrielaponciano.expenseapp.network.ExpenseControllerApi
import com.gabrielaponciano.expenseapp.ui.LocalStore
import com.gabrielaponciano.expenseapp.ui.states.AddExpenseUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class AddExpenseViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<AddExpenseUiState> = MutableStateFlow(AddExpenseUiState())
    val uiState = _uiState.asStateFlow()

    private val _expenseList = MutableStateFlow<List<Spending>>(emptyList())
    val expenseList: StateFlow<List<Spending>> = _expenseList.asStateFlow()

    var token: String = ""
    var userId: Int = 0

    fun setUserToken(token: String):String {
        this.token = token
        return token
    }
    fun setUserId(userId: Int):Int {
        this.userId = userId
        return userId
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun addExpense(name: String, value: Float, userId: Int, day: LocalDateTime, token: String) {
        val newSpending = createSpending(name, value, userId, day)
        viewModelScope.launch {
            try {
                val response = ExpenseControllerApi.createSpendingUser(newSpending, token)
                _expenseList.value = _expenseList.value + listOf(newSpending)
                Log.d("Teste","${expenseList.value}")
            } catch (e: Exception) {
                Log.e("Error", "Failed to add expense: ${e.message}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadExpenses(userId: Int, token: String) {
        viewModelScope.launch {
            try {
                val expenses = ExpenseControllerApi.getSpendingsByUserId(userId, token)
                _expenseList.value = expenses.data
            } catch (e: Exception) {
                Log.e("Error", "Failed to load expenses: ${e.message}")
            }
        }
    }

    fun removeExpense(spending: Spending) {
        viewModelScope.launch {
            try {
                Log.d("RemoveExpense", "Tentando remover despesa com ID: ${spending.id}")
                val response = ExpenseControllerApi.deleteSpendingUser(spending.id, token)
                _expenseList.value = _expenseList.value.filter { it.id != spending.id }
                Log.d("DeleteExpense", "Despesa removida com sucesso: ${spending.id}")
            } catch (e: Exception) {
                Log.e("Error", "Erro ao remover despesa: ${e.message}")
            }
        }
    }

    fun expenseName(name:String){
        _uiState.update { currentState ->
            currentState.copy(
                name = name
            ) }
    }
    fun expenseDate(date: Long?){
        _uiState.update { currentState ->
            currentState.copy(
                date = date
            ) }
    }
    fun expenseValue(value:Float){
        _uiState.update {currentState ->
            currentState.copy(
                value = value
            )
        }
    }

    fun clearFields(){
        expenseName("")
        expenseValue(0f)
        expenseDate(null)
    }
    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertStringToDate(dateString: String): String {
        return try {

            val originalFormat = DateTimeFormatter.ISO_DATE_TIME
            val targetFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy")

            val parsedDate = LocalDateTime.parse(dateString, originalFormat)
            parsedDate.format(targetFormat)
        } catch (e: Exception) {
            dateString
        }
    }

    val balance: StateFlow<Float> = _expenseList.map { expenses ->
        expenses.sumOf { it.value.toDouble() }.toFloat()
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0f)

    val lastExpense: StateFlow<Float?> = _expenseList.map { expenses ->
        expenses.lastOrNull()?.value
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

}