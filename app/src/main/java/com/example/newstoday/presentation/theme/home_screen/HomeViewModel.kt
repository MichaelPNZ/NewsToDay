package com.example.newstoday.presentation.theme.home_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.example.newstoday.domain.model.Article
import com.example.newstoday.domain.usecases.GetArticleByCategoryUseCase
import com.example.newstoday.domain.usecases.GetArticleFromCacheUseCase
import com.example.newstoday.domain.usecases.GetFavoriteCategoriesUseCase
import com.example.newstoday.domain.usecases.GetUserByIdUseCase
import com.example.newstoday.domain.usecases.SaveUserUseCase
import com.example.newstoday.utils.LoadResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getArticleByCategoryUseCase: GetArticleByCategoryUseCase,
    private val getFavoriteCategoriesUseCase: GetFavoriteCategoriesUseCase,
    private val getArticleFromCacheUseCase: GetArticleFromCacheUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    val imageLoader: ImageLoader,
) : ViewModel() {

    private val _categories: MutableState<ImmutableList<String>> =
        mutableStateOf(persistentListOf())
    val categories: State<ImmutableList<String>> = _categories

    private val _category: MutableState<String> =
        mutableStateOf(String())
    val category: State<String> = _category

    private val _favorite: MutableState<ImmutableList<Article>> =
        mutableStateOf(persistentListOf())
    val favorite: State<ImmutableList<Article>> = _favorite

    init {
        runBlocking  {
            val user = getUserByIdUseCase(1)
            if (user.favoriteCategories.isNotEmpty()) {
                _category.value = user.favoriteCategories.first()
                _categories.value = user.favoriteCategories.toImmutableList()
            } else {
                _category.value = "Random"
                _categories.value = listOf("Random").toImmutableList()
            }
        }
    }

    private val selectedCategoryFlow = getArticleByCategoryUseCase(_category.value)
    private val favoriteCategoryFlow = getFavoriteCategoriesUseCase(_categories.value)

    val selectedCategoryState = selectedCategoryFlow
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

    val favoriteCategoryState = favoriteCategoryFlow
        .map { result ->
            when (result) {
                is LoadResource.Success -> {
                    FavoriteCategoryState.FavoriteCategory(favoriteCategoryList = result.data?.toImmutableList())
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

    fun changeCategory(category: String) {
        _category.value = category
    }

    fun isSelectCheck(category: String): Boolean {
        return _category.value.contains(category)
    }

    fun isFavoriteCheck(checkString: String): Boolean {
        return _favorite.value.find { it.title == checkString } != null
    }

    fun changeFavoriteStatus(checkString: String) {
        viewModelScope.launch {
            val user = getUserByIdUseCase(1)
            user.articles.map { news ->
                if (news.title == checkString) {
                    val update = user.copy(articles = user.articles.minus(news))
                    saveUserUseCase(update)
                    _favorite.value = update.articles.toImmutableList()
                } else {
                    val update = user.copy(articles = user.articles.plus(news))
                    saveUserUseCase(update)
                    _favorite.value = update.articles.toImmutableList()
                }
            }
        }
    }
}