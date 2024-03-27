package com.example.newstoday.data.network

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
    @SerializedName("articles")
    val articles: List<ArticleDTO>? = null,
)