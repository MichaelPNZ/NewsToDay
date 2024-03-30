package com.example.newstoday.domain.model


data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val favoriteCategories: List<String>,
    val articles: List<Article>,
)