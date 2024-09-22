package com.gabrielaponciano.expenseapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gabrielaponciano.expenseapp.model.GroupCreate
import com.gabrielaponciano.expenseapp.model.GroupJoin
import com.gabrielaponciano.expenseapp.model.GroupLeave
import com.gabrielaponciano.expenseapp.model.LoginRequest
import com.gabrielaponciano.expenseapp.model.User
import com.gabrielaponciano.expenseapp.model.createSpending
import com.gabrielaponciano.expenseapp.network.ExpenseControllerApi
import com.gabrielaponciano.expenseapp.ui.screens.AddExpense
import com.gabrielaponciano.expenseapp.ui.screens.HomeScreen
import com.gabrielaponciano.expenseapp.ui.screens.LoginScreen
import com.gabrielaponciano.expenseapp.ui.screens.SignUpScreen
import com.gabrielaponciano.expenseapp.ui.states.LoginUiState
import com.gabrielaponciano.expenseapp.ui.states.SignUpUiState
import com.gabrielaponciano.expenseapp.ui.theme.ExpenseAppTheme
import com.gabrielaponciano.expenseapp.ui.viewModel.LoginViewModel
import com.gabrielaponciano.expenseapp.ui.viewModel.SignUpViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    var token: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            delay(10000L)
            //testRequest()
        }
        enableEdgeToEdge()
        setContent {
            ExpenseAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login"){
                    composable("home"){
                        HomeScreen(navController, SignUpUiState())
                    }
                    composable("add"){
                        AddExpense()
                    }
                    composable("login"){
                        LoginScreen(loginViewModel = LoginViewModel(), uiState = LoginUiState(),navController)
                    }
                    composable("sign"){
                        SignUpScreen(signUpViewModel = SignUpViewModel(), SignUpUiState(), navController)
                    }
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun testRequest() {
        try {
            // USER
            var user = User(name = "Gabriela", email = "gabriela.ponciano@gmail.com", password = "senha123")
            var createdUser = ExpenseControllerApi.createUser(user)
            var loginResponse = ExpenseControllerApi.loginUser(LoginRequest(user.email, user.password))
            val token = loginResponse.data.token

            // SPENDING
            val newSpending1 = createSpending(
                name = "Ifood",
                day = LocalDateTime.now(),
                value = 50F,
                userId = loginResponse.data.userId
            )
            val newSpending2 = createSpending(
                name = "Amazon",
                day = LocalDateTime.now(),
                value = 80F,
                userId = loginResponse.data.userId
            )
            ExpenseControllerApi.createSpendingUser(newSpending1, token)
            ExpenseControllerApi.createSpendingUser(newSpending2, token)

            var spendings = ExpenseControllerApi.getSpendingsByUserId(loginResponse.data.userId, token).data
            println(spendings)

            val editedSpending = spendings[0]
            editedSpending.value = 85.2F
            ExpenseControllerApi.updateSpendingUser(loginResponse.data.userId, editedSpending, token)

            spendings = ExpenseControllerApi.getSpendingsByUserId(loginResponse.data.userId, token).data
            println(spendings)

            // GROUP
            val newGroup = GroupCreate(
                userId = loginResponse.data.userId,
                name = "Grupo de teste",
                password = "456"
            )
            ExpenseControllerApi.createGroup(newGroup, token)

            val groups = ExpenseControllerApi.getAllGroups(token).data
            val group = ExpenseControllerApi.getGroupById(groups[0].id, token).data
            println(group)

            val groupLeave = GroupLeave(
                userId = loginResponse.data.userId,
                groupId = group.id
            )
            ExpenseControllerApi.leaveGroup(groupLeave, token)

            val groupJoin = GroupJoin(
                userId = loginResponse.data.userId,
                groupId = group.id,
                password = "456"
            )
            ExpenseControllerApi.joinGroup(groupJoin, token)
            println("Fim exemplo")
        } catch (exception: Exception) {
            Log.println(Log.ERROR, "TESTE", exception.message.toString())
        }
    }
}



