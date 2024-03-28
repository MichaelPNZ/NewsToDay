package com.example.newstoday.presentation.theme.home_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.example.newstoday.domain.model.Article
import com.example.newstoday.domain.model.User
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
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    val imageLoader: ImageLoader,
) : ViewModel() {

    private val _categories: MutableState<ImmutableList<User>> =
        mutableStateOf(persistentListOf())
    val categories: State<ImmutableList<User>> = _categories

    private val _category: MutableState<String> =
        mutableStateOf(String())
    val category: State<String> = _category

    init {
        runBlocking  {
            val user = getUserByIdUseCase(1)
            if (user.favoriteCategories.isNotEmpty()) {
                _category.value = user.favoriteCategories.first()
                _categories.value = listOf(user).toImmutableList()
            } else {
                _category.value = "Random"
                _categories.value = emptyList<User>().toImmutableList()
            }
        }
    }

    private val selectedCategoryFlow = getArticleByCategoryUseCase(_category.value)
    private val favoriteCategoryFlow = getFavoriteCategoriesUseCase(_categories.value[0].favoriteCategories)

    var selectedCategoryState = selectedCategoryFlow
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

    fun isFavoriteCheck(article: Article): Boolean {
        return if (_categories.value[0].articles.isEmpty()) {
            false
        } else if (_categories.value[0].articles.contains(article)) {
            true
        } else {
            false
        }
    }

    fun changeFavoriteStatus(article: Article) {
        viewModelScope.launch {
            val user = getUserByIdUseCase(1)
            if (user.articles.contains(article)) {
                val update = user.copy(articles = user.articles.minus(article))
                saveUserUseCase(update)
                _categories.value = listOf(update).toImmutableList()
            } else {
                val update = user.copy(articles = user.articles.plus(article))
                saveUserUseCase(update)
                _categories.value = listOf(update).toImmutableList()
            }
        }
    }
}