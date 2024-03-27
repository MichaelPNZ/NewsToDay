package com.example.newstoday.data.mapper

import com.example.newstoday.data.local.entity.UserDBO
import com.example.newstoday.domain.model.User

fun UserDBO.toUser() = User(
    id = id,
    email = email,
    password = password,
    favoriteCategories = favoriteCategories
)