package com.example.newstoday.presentation.theme.home_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import coil.ImageLoader
import com.example.newstoday.domain.usecases.GetArticleByCategoryUseCase
import com.example.newstoday.domain.usecases.GetFavoriteCategoriesUseCase
import com.example.newstoday.domain.usecases.GetUserByIdUseCase
import com.example.newstoday.domain.usecases.SaveArticleUseCase
import com.example.newstoday.utils.LoadResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getArticleByCategoryUseCase: GetArticleByCategoryUseCase,
    private val getFavoriteCategoriesUseCase: GetFavoriteCategoriesUseCase,
    private val saveArticleUseCase: SaveArticleUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    val imageLoader: ImageLoader,
) : ViewModel() {

    private val _categories: MutableState<ImmutableList<String>> =
        mutableStateOf(persistentListOf())
    val categories: State<ImmutableList<String>> = _categories

    private val _category: MutableState<String> =
        mutableStateOf(String())
    val category: State<String> = _category

    init {
        runBlocking  {
            val user = getUserByIdUseCase(1)
            _categories.value = user.favoriteCategories.toImmutableList()
            _category.value = user.favoriteCategories.first()
        }
        Log.i("!!!", "$categories")
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
}