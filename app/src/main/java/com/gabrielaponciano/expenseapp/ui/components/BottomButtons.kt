package com.gabrielaponciano.expenseapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gabrielaponciano.expenseapp.ui.theme.Purple40

@Composable
fun BottomButton(title:String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .height(48.dp)
                .width(327.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Purple40)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(text = title, fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}