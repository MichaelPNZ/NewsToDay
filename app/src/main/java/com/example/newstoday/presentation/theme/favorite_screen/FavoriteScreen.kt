package com.example.newstoday.presentation.theme.favorite_screen


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.newstoday.R
import com.example.newstoday.domain.model.Article
import com.example.newstoday.presentation.theme.home_screen.HomeViewModel
import com.example.newstoday.presentation.theme.ui.BlackPrimary
import com.example.newstoday.presentation.theme.ui.GreyPrimary

@Composable
fun FavoriteScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (Article) -> Unit,
) {
    val favoriteNewsCount = remember {
        mutableIntStateOf(homeViewModel.favoriteList.value.size)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = stringResource(id = R.string.Bookmarks), style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.W600,
                lineHeight = 32.sp
            ),
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.Saved_articles_to_the_library), style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 24.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
        )
        if (favoriteNewsCount.intValue > 0) {
            SavedNews(
                homeViewModel = homeViewModel,
                navigateToDetail = navigateToDetail
            )
        } else { NoNews() }
    }
}

@Composable
fun SavedNews(
    homeViewModel: HomeViewModel,
    navigateToDetail: (Article) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(homeViewModel.favoriteList.value.size) { index ->
            val news = homeViewModel.favoriteList.value[index]
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { navigateToDetail(news) },
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(96.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White, RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                    model = news.urlToImage,
                    contentDescription = null
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = news.author,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W400,
                            lineHeight = 20.sp,
                            color = GreyPrimary
                        ),
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                    )
                    Text(
                        text = news.title,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            lineHeight = 24.sp,
                            color = BlackPrimary
                        ),
                        maxLines = 2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                    )
                }
                Icon(
                    painterResource(id = R.drawable.favorite_icon),
                    tint = Color.Red,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(top = 16.dp, end = 16.dp)
                        .clickable {
                            homeViewModel.changeFavoriteStatus(news)
                        }
                )
            }
        }
    }
}

@Composable
fun NoNews() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(
                color = Color(0xFFEEF0FB),
                radius = 32.dp.toPx()
            )
        }
        Image(
            painter = painterResource(id = R.drawable.littlebook),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.Center)

        )
        Text(
            text = stringResource(id = R.string.You_havent_saved_any_articles_yet_Start_reading_and_bookmarking_them_now),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                lineHeight = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .padding(top = 200.dp, start = 60.dp, end = 60.dp)

        )
    }
}

