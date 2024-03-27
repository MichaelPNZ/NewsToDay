package com.example.newstoday.domain.usecases

import com.example.newstoday.domain.repository.ArticleRepository
import javax.inject.Inject

class GetFavoriteCategoriesUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    operator fun invoke(categories: List<String>) = repository.getFavoriteCategories(categories)
}