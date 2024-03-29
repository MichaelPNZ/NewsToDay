package com.example.newstoday.presentation.theme.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.newstoday.R
import com.example.newstoday.domain.model.Article
import com.example.newstoday.presentation.theme.ui.GreyDark
import com.example.newstoday.presentation.theme.ui.GreyLight

@Composable
fun DetailsNewsScreen(
    article: Article,
    viewModel: HomeViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {

    Column(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
        ) {
            AsyncImage(
                article.urlToImage, "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            "",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        viewModel.changeFavoriteStatus(article)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.favorite_icon), "",
                            tint = if (viewModel.isFavoriteCheck(article)) Color.Red else Color.White
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            painterResource(R.drawable.share_arrows), "",
                            tint = Color.White,

                            )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomStart),
            ) {
                Text(
                    text = article.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = article.author,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Text(
                    text = article.source.name,
                    fontSize = 14.sp,
                    color = GreyLight
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    "Results",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(article.content, fontSize = 16.sp, color = GreyDark)
            }
        }
    }
}

@Composable
fun DetailNewsPreview() {

}