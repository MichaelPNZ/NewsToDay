package com.example.newstoday.presentation.theme.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newstoday.domain.model.Article

@Composable
fun DetailsNewsScreen(article: Article) {
    Column(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
        ) {
            AsyncImage(
                article.urlToImage, "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomStart),
            ) {
                Text(
                    text = article.author,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Text(
                    text = article.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = article.source.name,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }

            Box(modifier = Modifier.fillMaxHeight(0.5f)) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(article.content)

                }
            }

    }
}


@Composable
fun DetailNewsPreview() {

}