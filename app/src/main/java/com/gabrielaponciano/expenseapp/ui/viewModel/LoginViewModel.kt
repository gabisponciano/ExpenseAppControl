package com.gabrielaponciano.expenseapp.ui.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielaponciano.expenseapp.model.LoginRequest
import com.gabrielaponciano.expenseapp.model.User
import com.gabrielaponciano.expenseapp.model.createSpending
import com.gabrielaponciano.expenseapp.network.ExpenseControllerApi
import com.gabrielaponciano.expenseapp.ui.LocalStore
import com.gabrielaponciano.expenseapp.ui.states.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class LoginViewModel: ViewModel(){
    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible = _isPasswordVisible.asStateFlow()

    fun loginUserName(userEmail:String){
        _uiState.update { currentState ->
            currentState.copy(
                userEmail = userEmail
            ) }
    }
    fun loginPassword(password:String){
        _uiState.update { currentState ->
            currentState.copy(
                password = password
            ) }
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }


    fun login(callback: (success: Boolean, token: String) -> Unit){
        viewModelScope.launch {
           try {
               val loginResponse = ExpenseControllerApi.loginUser(LoginRequest(uiState.value.userEmail, uiState.value.password))
               val token = loginResponse.data.token
               callback(true, token)
           }catch (e:Exception) {
               Log.d("Erro", error(e))
               callback(false, "")
           }
       }
    }
}