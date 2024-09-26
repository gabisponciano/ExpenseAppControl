package com.gabrielaponciano.expenseapp.ui.screens

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.gabrielaponciano.expenseapp.R
import com.gabrielaponciano.expenseapp.model.Spending
import com.gabrielaponciano.expenseapp.ui.LocalStore
import com.gabrielaponciano.expenseapp.ui.components.AddButton
import com.gabrielaponciano.expenseapp.ui.components.CardItem
import com.gabrielaponciano.expenseapp.ui.states.SignUpUiState
import com.gabrielaponciano.expenseapp.ui.viewModel.AddExpenseViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavController) {
    val activity = LocalContext.current as ComponentActivity
    val addExpenseViewModel: AddExpenseViewModel = viewModel(activity)
    //val addExpenseViewModel = viewModel<AddExpenseViewModel>()
    val expenses by addExpenseViewModel.expenseList.collectAsState()
    val balance by addExpenseViewModel.balance.collectAsState()
    val lastExpense by addExpenseViewModel.lastExpense.collectAsState()
    addExpenseViewModel.token = LocalStore.getToken(LocalContext.current).toString()

    Scaffold (
        bottomBar = {
            AddButton(){
                navController.navigate("add")
            }
        }
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            Box {
                Image(
                    painter = painterResource(R.drawable.ic_topbar),
                    contentDescription = ""
                )
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Hello!" ,
                        fontSize = 32.sp,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )

                    CardItem(
                        modifier = Modifier.padding(top = 16.dp),
                        balance = "$${String.format("%.2f", balance)}",
                        expense =  if (lastExpense != null) "$${String.format("%.2f", lastExpense)}" else "$0.00"
                    )
                }
            }
            Text(
                text = "Expenses",
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(expenses) { spending ->
                    ExpenseItem(spending)
                }

            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpenseItem(spending: Spending) {
    val activity = LocalContext.current as ComponentActivity
    val addExpenseViewModel: AddExpenseViewModel = viewModel(activity)
    val formattedDate = addExpenseViewModel.convertStringToDate(spending.day)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = spending.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "Amount: $${spending.value}", fontSize = 14.sp, color = Color.Red, fontWeight = FontWeight.Bold)
                Text(text = "Date: $formattedDate", fontSize = 14.sp)
            }

            IconButton(onClick = {
                addExpenseViewModel.removeExpense(spending)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Delete Expense",
                    tint = Color.Gray
                )
            }
        }
    }
}
