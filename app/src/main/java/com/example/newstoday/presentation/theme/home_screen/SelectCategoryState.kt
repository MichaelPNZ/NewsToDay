package com.example.newstoday.presentation.theme.home_screen

import com.example.newstoday.domain.model.Article
import kotlinx.collections.immutable.ImmutableList

sealed class SelectCategoryState {

    data object Initial : SelectCategoryState()

    data object Loading : SelectCategoryState()

    data object Error : SelectCategoryState()

    data class SelectCategory(
        val selectedCategoryList: ImmutableList<Article>?
    ) : SelectCategoryState()
}