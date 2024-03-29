package com.example.newstoday.domain.model


data class User(
    val id: Int,
    val email: String,
    val password: String,
    val favoriteCategories: List<String>,
    val articles: List<Article>,
)