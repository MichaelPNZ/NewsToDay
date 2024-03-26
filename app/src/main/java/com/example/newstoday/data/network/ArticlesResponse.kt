package com.example.newstoday.data.network

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
//    @SerializedName("status")
//    @Expose
//    val status: String,
//
//    @SerializedName("totalResults")
//    @Expose
//    val totalResults: Int,
//
    @SerializedName("articles")
    val articles: List<ArticleDTO>? = null,
)