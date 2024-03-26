package com.example.newstoday.data.network

import com.example.newstoday.domain.model.Source
import com.google.gson.annotations.SerializedName

data class ArticleDTO(
    @SerializedName("source")
    val source: Source? = null,

    @SerializedName("author")
    val author: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("urlToImage")
    val urlToImage: String? = null,

    @SerializedName("publishedAt")
    val publishedAt: String? = null,

    @SerializedName("content")
    val content: String? = null,
)