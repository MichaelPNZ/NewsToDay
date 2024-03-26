package com.example.newstoday.presentation.theme.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.newstoday.domain.model.Article
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun TestScreen(
    viewModel: TestViewModel = hiltViewModel(),
) {
    val screenState = viewModel.screenState.collectAsStateWithLifecycle(TestState.Initial)

    TestScreenContent(
        screenState = screenState,
        viewModel = viewModel)

}

@Composable
fun TestScreenContent(
    screenState: State<TestState>,
    viewModel: TestViewModel,
) {
    when (val currentState = screenState.value) {
        is TestState.Initial -> {}
        is TestState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is TestState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error")
            }
        }

        is TestState.Articles -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                NewsList(
                    news = currentState.articles?.filter { it.urlToImage.isNotEmpty() }
                        ?.toImmutableList(),
                    viewModel = viewModel)
            }
        }
    }
}

@Composable
fun NewsList(
    news: ImmutableList<Article>?,
    viewModel: TestViewModel,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
        news?.let {
            items(items = news) {
                NewsItem(
                    article = it,
                    viewModel = viewModel)
            }
        }
    }
}

@Composable
fun NewsItem(
    article: Article,
    viewModel: TestViewModel,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(1f)
                    .background(Color.White, RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
                imageLoader = viewModel.imageLoader,
                model = article.urlToImage,
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomStart),
            ) {
                Text(text = article.author,
                    fontSize = 16.sp,
                    color = Color.White)
                Text(text = article.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White)
                Text(text = article.source.name,
                    fontSize = 14.sp,
                    color = Color.White)
            }
        }
    }
}