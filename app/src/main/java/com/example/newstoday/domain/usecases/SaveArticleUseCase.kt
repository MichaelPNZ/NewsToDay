package com.example.newstoday.domain.usecases

import com.example.newstoday.data.local.entity.ArticleDBO
import com.example.newstoday.domain.repository.ArticleRepository
import javax.inject.Inject

class SaveArticleUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(result: ArticleDBO) = repository.saveArticle(result)
}