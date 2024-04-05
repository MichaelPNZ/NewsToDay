@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.newstoday.presentation.theme.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.newstoday.R
import com.example.newstoday.domain.model.Article
import com.example.newstoday.presentation.theme.ui.GreyLighter
import com.example.newstoday.presentation.theme.ui.GreyPrimary
import com.example.newstoday.presentation.theme.ui.PurplePrimary
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (Article) -> Unit,
    navigateToDetail2: (List<Article>) -> Unit,
) {
    val selectedCategoryState =
        viewModel.selectedCategoryState().collectAsStateWithLifecycle(SelectCategoryState.Initial)
    val favoriteCategoryState =
        viewModel.favoriteCategoryState().collectAsStateWithLifecycle(FavoriteCategoryState.Initial)

    HomeScreenContent(
        selectedCategoryState = selectedCategoryState,
        favoriteCategoryState = favoriteCategoryState,
        viewModel = viewModel,
        navigateToDetail = navigateToDetail,
        navigateToDetail2 = navigateToDetail2

    )
}

@Composable
fun HomeScreenContent(
    selectedCategoryState: State<SelectCategoryState>,
    favoriteCategoryState: State<FavoriteCategoryState>,
    viewModel: HomeViewModel,
    navigateToDetail: (Article) -> Unit,
    navigateToDetail2: (List<Article>) -> Unit,
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var recList = emptyList<Article>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            "Browse",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 72.dp, start = 20.dp),
            lineHeight = 32.sp
        )
        Text(
            "Discover things of this world", color = GreyPrimary, fontSize = 16.sp,
            modifier = Modifier.padding(top = 5.dp, start = 20.dp),
            lineHeight = 24.sp
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { newQuery ->
                searchQuery = newQuery
                viewModel.searchArticles(newQuery)
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 30.dp),
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

        LazyRow(modifier = Modifier.padding(top = 20.dp, start = 15.dp)) {
            items(viewModel.favoriteCategoryList.value) { category ->
                Button(
                    onClick = {
                        viewModel.changeCategory(category)
                    },
                    colors = ButtonDefaults.buttonColors(
                        if (viewModel.isSelectCheck(category)) PurplePrimary else GreyLighter
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .padding(end = 8.dp, start = 8.dp),
                    elevation = ButtonDefaults.buttonElevation(5.dp)
                ) {
                    Text(
                        category,
                        color = if (viewModel.isSelectCheck(category)) Color.White else GreyPrimary,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 20.dp))

        when (val currentState = selectedCategoryState.value) {
            is SelectCategoryState.Initial -> {}
            is SelectCategoryState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is SelectCategoryState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error")
                }
            }

            is SelectCategoryState.SelectCategory -> {
                NewsList(currentState.selectedCategoryList?.filter { it.urlToImage.isNotEmpty() }
                    ?.toImmutableList(),
                    viewModel = viewModel,
                    navigateToDetail)
            }
        }
        Spacer(modifier = Modifier.padding(20.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
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
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable {
                        navigateToDetail2(recList)
                    }
            )
        }

        Spacer(modifier = Modifier.padding(14.dp))
        when (val currentFavoriteState = favoriteCategoryState.value) {
            is FavoriteCategoryState.Initial -> {}
            is FavoriteCategoryState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is FavoriteCategoryState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error")
                }
            }

            is FavoriteCategoryState.FavoriteCategory -> {
                val data =
                    currentFavoriteState.favoriteCategoryList?.filter { it.urlToImage.isNotEmpty() }
                if (data != null) {
                    recList = data
                }
                RecommendItem(data, navigateToDetail)
            }
        }
    }
}

@Composable
fun NewsList(
    news: ImmutableList<Article>?,
    viewModel: HomeViewModel,
    navigateToDetail: (Article) -> Unit,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
    ) {
        news?.let {
            items(items = news) {

                NewsItems(
                    article = it,
                    viewModel = viewModel,
                    navigateToDetail
                )
            }
        }
    }
}


@Composable
fun NewsItems(
    article: Article,
    viewModel: HomeViewModel,
    navigateToDetail: (Article) -> Unit
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
                    text = viewModel.category.value,
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

@Composable
fun RecommendItem(
    data: List<Article>?,
    navigateToDetail: (Article) -> Unit
) {
    repeat(3) { item ->
        data?.let {
            Card(modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                .clickable { navigateToDetail(data[item]) },
                colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Row(
                ) {
                    AsyncImage(
                        data[item].urlToImage, "",
                        modifier = Modifier
                            .size(96.dp, 96.dp)
                            .clip(RoundedCornerShape(15.dp)),
                        contentScale = ContentScale.Crop,
                    )
                    Column(modifier = Modifier.padding(start = 15.dp)) {
                        Text(
                            data[item].author, fontSize = 14.sp,
                            color = GreyPrimary,
                            lineHeight = 20.sp,
                        )
                        Text(
                            data[item].title + ".....",
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
