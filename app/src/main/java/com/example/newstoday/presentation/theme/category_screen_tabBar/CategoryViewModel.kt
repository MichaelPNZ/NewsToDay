package com.example.newstoday.presentation.theme.category_screen_tabBar

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newstoday.domain.usecases.GetUserByIdUseCase
import com.example.newstoday.domain.usecases.SaveUserUseCase
import com.example.newstoday.presentation.theme.home_screen.HomeViewModel
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

    val categoriesList = listOf(
        "Sports",
        "Politics",
        "Life",
        "Gaming",
        "Animals",
        "Nature",
        "Food",
        "Art",
        "History",
        "Fashion",
        "Covid-19",
        "Middle East"
    )

    val emojiList = listOf(
        "\uD83C\uDFC8   ",
        "⚖\uFE0F   ",
        "\uD83C\uDF1E   ",
        "\uD83C\uDFAE   ",
        "\uD83D\uDC3B   ",
        "\uD83C\uDF34   ",
        "\uD83C\uDF54   ",
        "\uD83C\uDFA8   ",
        "\uD83D\uDCDC   ",
        "\uD83D\uDC57   ",
        "\uD83D\uDE37   ",
        "⚔\uFE0F   "
    )

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
