package com.example.newstoday.data.repository

import com.example.newstoday.data.mapper.toArticle
import com.example.newstoday.data.network.ApiService
import com.example.newstoday.domain.model.Article
import com.example.newstoday.domain.repository.ArticleRepository
import com.example.newstoday.utils.LoadResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
    private val  apiService: ApiService
) : ArticleRepository {


    override fun getArticles(): Flow<LoadResource<List<Article>?>> {
        return flow {
            try {
                val articles = apiService.getCategories().articles?.map { it.toArticle() }
                emit(LoadResource.Success(articles))
            } catch (e: Exception) {
                emit(LoadResource.Error("Неизвестная ошибка"))
            }
        }
    }

    override fun getCategory(category: String): Flow<LoadResource<List<Article>?>> {
        return flow {
            try {
                val articles = apiService.getCategory(category).articles?.map { it.toArticle() }
                emit(LoadResource.Success(articles))
            } catch (e: Exception) {
                emit(LoadResource.Error("Неизвестная ошибка"))
            }
        }
    }

    override fun getFavoriteCategories(categories: List<String>): Flow<LoadResource<List<Article>?>> {
        return flow {
            try {
                val articles = apiService.getFavoriteCategories(categories).articles?.map { it.toArticle() }
                emit(LoadResource.Success(articles))
            } catch (e: Exception) {
                emit(LoadResource.Error("Неизвестная ошибка"))
            }
        }
    }
}