package com.example.newstoday.presentation.theme.category_screen_tabBar

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newstoday.domain.usecases.GetUserByIdUseCase
import com.example.newstoday.domain.usecases.SaveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getUsersUseCase: GetUserByIdUseCase,
    private val saveUserUseCase: SaveUserUseCase,
) : ViewModel() {

    private val _categories: MutableState<ImmutableList<String>> =
        mutableStateOf(persistentListOf())
    val categories: State<ImmutableList<String>> = _categories

    init {
        viewModelScope.launch {
            val user = getUsersUseCase(1)
            _categories.value = user.favoriteCategories.toImmutableList()
        }
    }

    fun isSelectCheck(category: String): Boolean {
        return _categories.value.contains(category)
    }

    fun toggleCategory(category: String) {
        viewModelScope.launch {
            val user = getUsersUseCase(1)
            if (user.favoriteCategories.contains(category)) {
                val update = user.copy(favoriteCategories = user.favoriteCategories.minus(category))
                saveUserUseCase(update)
                _categories.value = update.favoriteCategories.toImmutableList()
            } else if (user.id == 1 && !user.favoriteCategories.contains(category)) {
                val update = user.copy(favoriteCategories = user.favoriteCategories.plus(category))
                saveUserUseCase(update)
                _categories.value = update.favoriteCategories.toImmutableList()
            }
        }
    }
}
