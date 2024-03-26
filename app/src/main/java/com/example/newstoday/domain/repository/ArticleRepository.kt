package com.example.newstoday.domain.repository

import com.example.newstoday.domain.model.Article
import com.example.newstoday.utils.LoadResource
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    fun getArticles(): Flow<LoadResource<List<Article>?>>
}