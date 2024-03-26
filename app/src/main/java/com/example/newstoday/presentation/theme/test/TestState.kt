package com.example.newstoday.presentation.theme.test

import com.example.newstoday.domain.model.Article
import kotlinx.collections.immutable.ImmutableList

sealed class TestState {

    data object Initial : TestState()

    data object Loading : TestState()

    data object Error : TestState()

    data class Articles(
        val articles: ImmutableList<Article>?
    ) : TestState()
}