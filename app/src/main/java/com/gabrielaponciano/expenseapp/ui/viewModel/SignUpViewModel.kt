package com.gabrielaponciano.expenseapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielaponciano.expenseapp.model.LoginRequest
import com.gabrielaponciano.expenseapp.model.User
import com.gabrielaponciano.expenseapp.network.ExpenseControllerApi
import com.gabrielaponciano.expenseapp.ui.states.SignUpUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel: ViewModel(){
    private val _uiState: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible = _isPasswordVisible.asStateFlow()

    fun SignUpUserName(userName:String){
        _uiState.update { currentState ->
            currentState.copy(
                userName = userName
            ) }
    }

    fun SignUpEmail(userEmail:String){
        _uiState.update { currentState ->
            currentState.copy(
                userEmail = userEmail
            ) }
    }

    fun SignUpPassword(password:String){
        _uiState.update { currentState ->
            currentState.copy(
                password = password
            ) }
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }
    fun signUp(name:String,email:String, password: String, callback: (success: Boolean) -> Unit){
        viewModelScope.launch {
            try {
                var user = User(name = name, email = email, password = password)
                var createdUser = ExpenseControllerApi.createUser(user)
                callback(true)
            }catch (e:Exception) {
                Log.d("Erro", error(e))
                callback(false)
            }
        }
    }
}