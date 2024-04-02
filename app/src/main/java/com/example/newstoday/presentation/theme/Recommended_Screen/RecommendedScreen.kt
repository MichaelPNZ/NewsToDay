package com.example.newstoday.presentation.theme.Recommended_Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.newstoday.presentation.theme.home_screen.NewsItems
import com.example.newstoday.presentation.theme.home_screen.recList

@Composable
fun RecScreen(
    articles: recList,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (Article) -> Unit
) {
    LazyVerticalGrid(GridCells.Fixed(2), modifier = Modifier.fillMaxSize().padding(top = 20.dp)) {
        articles.a.let {
            items(articles.a) { b ->
                NewsItems2(b, viewModel, navigateToDetail)
            }
        }

    }


}

@Composable
fun NewsItems2(
    article: Article,
    viewModel: HomeViewModel,
    navigateToDetail: (Article) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .height(200.dp)
                .width(200.dp),
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
        }
    }
}
