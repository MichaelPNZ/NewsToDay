package com.example.newstoday.presentation.theme.favorite_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newstoday.R

val Kolvo = 4
@Preview(showBackground = true)
@Composable
fun FavoriteScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Text(
            text = "Bookmarks", style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.W600,
                lineHeight = 32.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 72.dp, start = 20.dp)
        )
        Text(
            text = "Saved articles to the library", style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 24.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 112.dp, start = 20.dp)
        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp, bottom = 16.dp, start = 32.dp, end = 32.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(Kolvo) {
 
            }
        }
    }

}
