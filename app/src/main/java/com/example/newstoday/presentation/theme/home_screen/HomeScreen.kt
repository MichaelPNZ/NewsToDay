@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.newstoday.presentation.theme.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.newstoday.R
import com.example.newstoday.domain.model.Article
import com.example.newstoday.presentation.theme.test.TestState
import com.example.newstoday.presentation.theme.test.TestViewModel
import com.example.newstoday.presentation.theme.ui.GreyLighter
import com.example.newstoday.presentation.theme.ui.GreyPrimary
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList


@Composable
fun HomeScreen(viewModel: TestViewModel = hiltViewModel()) {
    val screenState = viewModel.screenState.collectAsStateWithLifecycle(TestState.Initial)
    val searchText = remember { mutableStateOf("") }
    var buttonColor by remember { mutableStateOf(GreyLighter) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            "Browse",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 70.dp, start = 20.dp),
            lineHeight = 32.sp
        )
        Text(
            "Discover things of this world", color = GreyPrimary, fontSize = 16.sp,
            modifier = Modifier.padding(top = 5.dp, start = 20.dp),
            lineHeight = 24.sp
        )

        OutlinedTextField(
            "",
            onValueChange = { searchText.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 40.dp),
            shape = RoundedCornerShape(12.dp),

            placeholder = { Text("Search", color = GreyPrimary) },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_search_24), "",
                    tint = GreyPrimary
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = GreyLighter,
                unfocusedContainerColor = GreyLighter,
                disabledContainerColor = GreyLighter,
                errorContainerColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,

                )
        )
        val categoryList = listOf("Random", "Apple", "Tesla", "Business", "TechCrunch", "News")
        LazyRow(modifier = Modifier.padding(top = 20.dp, start = 15.dp)) {
            items(categoryList) { category ->
                Button(
                    onClick = {
                    }, colors = ButtonDefaults.buttonColors(buttonColor),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .padding(end = 8.dp, start = 8.dp),
                    elevation = ButtonDefaults.buttonElevation(5.dp)
                ) {
                    Text(category, color = GreyPrimary, fontSize = 10.sp)
                }


            }
        }
        ScreenContents(screenState = screenState, viewModel = viewModel)
    }
}


@Composable
fun ScrollNews(
    news: ImmutableList<Article>?,
    viewModel: TestViewModel,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            news?.let {
                items(items = news) {
                    NewsItems(
                        article = it,
                        viewModel = viewModel
                    )
                }
            }

        }
        Spacer(modifier = Modifier.padding(30.dp))
        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                "Recommended for you",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 20.dp)
            )
            Text(
                "See All",
                fontSize = 14.sp,
                color = GreyPrimary,
                modifier = Modifier.padding(end = 20.dp)
            )
        }
    }

}

@Composable
fun NewsItems(
    article: Article,
    viewModel: TestViewModel,
) {
    Spacer(modifier = Modifier.padding(start = 20.dp))
    Card(
        modifier = Modifier
            .height(256.dp)
            .width(256.dp),
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
            IconButton(onClick = {

            }, modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(
                    painter = painterResource(R.drawable.favorite_icon), "favorites",
                    tint = Color.White
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

@Composable
fun ScreenContents(
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
            Box(modifier = Modifier.padding(top = 20.dp), contentAlignment = Alignment.Center) {
                ScrollNews(
                    news = currentState.articles?.filter { it.urlToImage.isNotEmpty() }
                        ?.toImmutableList(),
                    viewModel = viewModel)
            }
        }
    }
}
