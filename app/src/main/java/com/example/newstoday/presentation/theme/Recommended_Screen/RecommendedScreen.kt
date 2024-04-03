package com.example.newstoday.presentation.theme.Recommended_Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.newstoday.R
import com.example.newstoday.domain.model.Article
import com.example.newstoday.presentation.theme.home_screen.HomeViewModel
import com.example.newstoday.presentation.theme.ui.GreyPrimary

@Composable
fun RecScreen(
    articles: List<Article>,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (Article) -> Unit
) {

    RecommendItem2(articles, viewModel, navigateToDetail)


}

@Composable
fun RecommendItem2(
    data: List<Article>?,
    viewModel: HomeViewModel,
    navigateToDetail: (Article) -> Unit
) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = 20.dp)
        .background(Color.White)) {
        data?.let {
            items(data) { item ->
                if (item.urlToImage.isNotEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                            .clickable { navigateToDetail(item) },
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                        ) {
                            AsyncImage(
                                item.urlToImage, "",
                                modifier = Modifier
                                    .size(96.dp, 96.dp)
                                    .clip(RoundedCornerShape(15.dp)),
                                contentScale = ContentScale.Crop,
                            )
                            Column(modifier = Modifier.padding(start = 15.dp)) {
                                Text(
                                    viewModel.category.value, fontSize = 14.sp,
                                    color = GreyPrimary,
                                    lineHeight = 20.sp,
                                )
                                Text(
                                    item.title + ".....",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    maxLines = 3
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}


