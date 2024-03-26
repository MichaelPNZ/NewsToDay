package com.example.newstoday.presentation.theme.test

import androidx.lifecycle.ViewModel
import coil.ImageLoader
import com.example.newstoday.domain.usecases.GetArticlesUseCase
import com.example.newstoday.utils.LoadResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class TestViewModel@Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    val imageLoader: ImageLoader,
) : ViewModel() {

    private val articlesFlow = getArticlesUseCase()

    val screenState = articlesFlow
        .map { result ->
            when (result) {
                is LoadResource.Success -> {
                    TestState.Articles(articles = result.data?.toImmutableList())
                }
                is LoadResource.Error -> {
                    TestState.Error
                }
                is LoadResource.Loading -> {
                    TestState.Loading
                }
            }
        }
        .onStart { emit(TestState.Loading) }
}