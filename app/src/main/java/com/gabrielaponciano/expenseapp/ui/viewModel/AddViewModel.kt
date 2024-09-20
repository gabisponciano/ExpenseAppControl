package com.gabrielaponciano.expenseapp.ui.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielaponciano.expenseapp.model.Spending
import com.gabrielaponciano.expenseapp.model.createSpending
import com.gabrielaponciano.expenseapp.network.ExpenseControllerApi
import com.gabrielaponciano.expenseapp.ui.states.AddExpenseUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
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
                _expenseList.value = _expenseList.value + newSpending
            } catch (e: Exception) {
                Log.d("Erro", error(e))
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

}