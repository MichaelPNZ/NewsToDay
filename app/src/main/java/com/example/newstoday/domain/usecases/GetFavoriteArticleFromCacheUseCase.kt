package com.example.newstoday.domain.usecases

import com.example.newstoday.domain.repository.ArticleRepository
import javax.inject.Inject

class GetFavoriteArticleFromCacheUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke() = repository.getFavoriteArticlesFromCache()
}