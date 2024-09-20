package com.gabrielaponciano.expenseapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabrielaponciano.expenseapp.R
import com.gabrielaponciano.expenseapp.ui.theme.Purple40

@Composable
fun AddButton(onClick: () -> Unit){
    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp)
        .background(Purple40)
        .size(56.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        Image(painter = painterResource(R.drawable.ic_addbutton), contentDescription = "",
            modifier = Modifier.shadow(8.dp).clickable { onClick() })

    }
}
@Composable
@Preview
fun AddButtonPreview(){
    AddButton(){

    }
}