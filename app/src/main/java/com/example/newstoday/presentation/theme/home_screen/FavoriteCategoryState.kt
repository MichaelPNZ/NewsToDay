package com.example.newstoday.presentation.theme.home_screen


import com.example.newstoday.domain.model.Article
import kotlinx.collections.immutable.ImmutableList


sealed class FavoriteCategoryState {

    data object Initial : FavoriteCategoryState()

    data object Loading : FavoriteCategoryState()

    data object Error : FavoriteCategoryState()
    data class FavoriteCategory(
        val favoriteCategoryList: ImmutableList<Article>?
    ) : FavoriteCategoryState()
}