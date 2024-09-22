package com.gabrielaponciano.expenseapp.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gabrielaponciano.expenseapp.ui.components.BottomButton
import com.gabrielaponciano.expenseapp.ui.states.SignUpUiState
import com.gabrielaponciano.expenseapp.ui.theme.BackField
import com.gabrielaponciano.expenseapp.ui.theme.TextFieldBackground
import com.gabrielaponciano.expenseapp.ui.viewModel.SignUpViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(signUpViewModel: SignUpViewModel, uiState: SignUpUiState, navController: NavController){
    val uiState by signUpViewModel.uiState.collectAsState()
    val passwordVisible by signUpViewModel.isPasswordVisible.collectAsState()
    val context = LocalContext.current
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Sign Up", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.Close , contentDescription = "Fechar")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White))
        },
        bottomBar = {
            BottomButton(title = "Fazer Sign Up") {
                signUpViewModel.signUp(
                    name = uiState.userName,
                    email = uiState.userEmail,
                    password = uiState.password
                ) { success ->
                    if (success) {
                        Log.d("SignUp", "User signed up successfully")
                        Toast.makeText(context, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                        navController.navigate("home")
                    } else {
                        // Handle error
                        Log.d("SignUp", "Error signing up user")
                    }
                }
            }
        }
    ){ paddingValues ->
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ){
            HorizontalDivider(modifier = Modifier.height(96.dp))
            OutlinedTextField(
                value = uiState.userName,
                onValueChange = { signUpViewModel.SignUpUserName(it)

                },
                label = { Text(text = "Nome de Usúario") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = BackField,
                    unfocusedContainerColor = BackField,
                    focusedBorderColor = TextFieldBackground,
                    unfocusedBorderColor= TextFieldBackground
                ),
                modifier = Modifier.width(320.dp)
            )
            OutlinedTextField(
                value = uiState.userEmail,
                onValueChange = { signUpViewModel.SignUpEmail(it)
                },
                label = { Text(text = "Email") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = BackField,
                    unfocusedContainerColor = BackField,
                    focusedBorderColor = TextFieldBackground,
                    unfocusedBorderColor= TextFieldBackground
                ),
                modifier = Modifier.width(320.dp)
            )
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { signUpViewModel.SignUpPassword(it)
                },
                label = { Text(text = "Senha") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = BackField,
                    unfocusedContainerColor = BackField,
                    focusedBorderColor = TextFieldBackground,
                    unfocusedBorderColor= TextFieldBackground
                ),
                modifier = Modifier.width(320.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Default.Check else Icons.Default.Close
                    IconButton(onClick = { signUpViewModel.togglePasswordVisibility() }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                }
            )
        }
    }
}
@Preview
@Composable
fun SignUpPreview(){
    SignUpScreen(signUpViewModel = SignUpViewModel(), uiState = SignUpUiState(), rememberNavController())
}