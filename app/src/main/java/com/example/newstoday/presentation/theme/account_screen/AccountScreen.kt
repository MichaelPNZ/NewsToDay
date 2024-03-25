package com.example.newstoday.presentation.theme.account_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AccountScreen() {
    Text(
        modifier = Modifier.padding(16.dp)
            .fillMaxSize(),
        text = "AccountScreen",
        textAlign = TextAlign.Center,
        fontSize = 24.sp,)
}

@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    AccountScreen()
}
