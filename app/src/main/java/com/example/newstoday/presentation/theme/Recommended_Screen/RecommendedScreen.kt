package com.example.newstoday.presentation.theme.Recommended_Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        .background(Color.White)
    ) {
        data?.let {
            items(data) { item ->
                if (item.urlToImage.isNotEmpty()) {
                    RecommendItems(article = item, viewModel = viewModel) {

                    }
//                    Card(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(horizontal = 20.dp, vertical = 5.dp)
//                            .clickable { navigateToDetail(item) },
//                        colors = CardDefaults.cardColors(containerColor = Color.White)
//                    ) {
//                        Row(
//                        ) {
//                            AsyncImage(
//                                item.urlToImage, "",
//                                modifier = Modifier
//                                    .size(96.dp, 96.dp)
//                                    .clip(RoundedCornerShape(15.dp)),
//                                contentScale = ContentScale.Crop,
//                            )
//                            Column(modifier = Modifier.padding(start = 15.dp)) {
//                                Text(
//                                    viewModel.category.value, fontSize = 14.sp,
//                                    color = GreyPrimary,
//                                    lineHeight = 20.sp,
//                                )
//                                Text(
//                                    item.title + ".....",
//                                    fontWeight = FontWeight.Bold,
//                                    fontSize = 16.sp,
//                                    lineHeight = 24.sp,
//                                    maxLines = 3
//                                )
//                            }
//                        }
//                    }
                }
            }
        }
    }

}

@Composable
fun RecommendItems(
    article: Article,
    viewModel: HomeViewModel,
    navigateToDetail: (Article) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .aspectRatio(1f)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(1f)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .clickable {
                        navigateToDetail(article)
                    },
                contentScale = ContentScale.Crop,
                model = article.urlToImage,
                contentDescription = null
            )
            IconButton(
                onClick = {
                    viewModel.changeFavoriteStatus(article)
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    painter = painterResource(R.drawable.favorite_icon), "favorites",
                    tint = if (viewModel.isFavoriteCheck(article)) Color.Red else Color.White
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
                    .align(Alignment.BottomStart),
            ) {
                Text(
                    text = article.author,
                    fontSize = 16.sp,
                    color = Color.White,
                    lineHeight = 24.sp,
                )
                Text(
                    text = article.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}


