package com.example.newstoday.presentation.theme.home_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.example.newstoday.domain.model.Article
import com.example.newstoday.domain.usecases.GetArticleByCategoryUseCase
import com.example.newstoday.domain.usecases.GetFavoriteCategoriesUseCase
import com.example.newstoday.domain.usecases.GetIsLoginUserUseCase
import com.example.newstoday.domain.usecases.SaveUserUseCase
import com.example.newstoday.utils.LoadResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getIsLoginUserUseCase: GetIsLoginUserUseCase,
    private val getArticleByCategoryUseCase: GetArticleByCategoryUseCase,
    private val getFavoriteCategoriesUseCase: GetFavoriteCategoriesUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    val imageLoader: ImageLoader,
) : ViewModel() {

    private val _favoriteList: MutableState<ImmutableList<Article>> =
        mutableStateOf(persistentListOf())
    val favoriteList: State<ImmutableList<Article>> = _favoriteList

    private val _category: MutableState<String> =
        mutableStateOf(String())
    val category: State<String> = _category

    private val _favoriteCategoryList: MutableState<ImmutableList<String>> =
        mutableStateOf(persistentListOf())
    val favoriteCategoryList: State<ImmutableList<String>> = _favoriteCategoryList

    init {
        runBlocking  {
            val user = getIsLoginUserUseCase()
            if (user.favoriteCategories.isNotEmpty()) {
                _category.value = user.favoriteCategories.first()
                _favoriteList.value = user.articles.toImmutableList()
                _favoriteCategoryList.value = user.favoriteCategories.toImmutableList()
            }
        }
        observeDatabaseChanges()
    }

    fun selectedCategoryState(): Flow<SelectCategoryState> {
        return getArticleByCategoryUseCase(_category.value)
            .map { result ->
                when (result) {
                    is LoadResource.Success -> {
                        SelectCategoryState.SelectCategory(selectedCategoryList = result.data?.toImmutableList())
                    }
                    is LoadResource.Error -> {
                        SelectCategoryState.Error
                    }
                    is LoadResource.Loading -> {
                        SelectCategoryState.Loading
                    }
                }
            }
            .onStart { emit(SelectCategoryState.Loading) }
    }

    fun favoriteCategoryState(): Flow<FavoriteCategoryState> {
        return getFavoriteCategoriesUseCase(_favoriteCategoryList.value)
            .map { result ->
                when (result) {
                    is LoadResource.Success -> {
                        FavoriteCategoryState.FavoriteCategory(
                            favoriteCategoryList = result.data?.shuffled()?.toImmutableList()
                        )
                    }
                    is LoadResource.Error -> {
                        FavoriteCategoryState.Error
                    }
                    is LoadResource.Loading -> {
                        FavoriteCategoryState.Loading
                    }
                }
            }
            .onStart { emit(FavoriteCategoryState.Loading) }
    }

    fun changeCategory(category: String) {
        _category.value = category
    }

    fun isSelectCheck(category: String): Boolean {
        return _category.value.contains(category)
    }

    fun isFavoriteCheck(article: Article): Boolean {
        return if (_favoriteList.value.isEmpty()) {
            false
        } else if (_favoriteList.value.contains(article)) {
            true
        } else {
            false
        }
    }

    fun changeFavoriteStatus(article: Article) {
        viewModelScope.launch {
            val user = getIsLoginUserUseCase()
            if (user.articles.contains(article)) {
                val update = user.copy(articles = user.articles.minus(article))
                saveUserUseCase(update)
                _favoriteList.value = _favoriteList.value.minus(article).toImmutableList()
            } else {
                val update = user.copy(articles = user.articles.plus(article))
                saveUserUseCase(update)
                _favoriteList.value = _favoriteList.value.plus(article).toImmutableList()
            }
        }
    }

    fun searchArticles(query: String) {
        _category.value = query
    }

    private fun observeDatabaseChanges() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                val updatedUser = getIsLoginUserUseCase()
                _favoriteList.value = updatedUser.articles.toImmutableList()
                _favoriteCategoryList.value = updatedUser.favoriteCategories.toImmutableList()
            }
        }
    }
}