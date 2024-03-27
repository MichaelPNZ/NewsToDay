package com.example.newstoday.domain.usecases

import com.example.newstoday.domain.repository.ArticleRepository
import javax.inject.Inject

class GetArticleByCategoryUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    operator fun invoke(category: String) = repository.getCategory(category)
}