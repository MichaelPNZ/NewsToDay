package com.example.newstoday.domain.repository

import com.example.newstoday.data.local.entity.ArticleDBO
import com.example.newstoday.domain.model.Article
import com.example.newstoday.utils.LoadResource
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    fun getArticles(): Flow<LoadResource<List<Article>?>>
    fun getCategory(category: String): Flow<LoadResource<List<Article>?>>
    fun getSelectedCategories(categories: List<String>): Flow<LoadResource<List<Article>?>>
    fun getFavoriteCategories(categories: List<String>): Flow<LoadResource<List<Article>?>>

    suspend fun saveArticle(result: ArticleDBO)
    suspend fun getArticlesFromCache(): List<ArticleDBO>
    suspend fun getFavoriteArticlesFromCache(): ArticleDBO?
}