package com.example.newstoday.domain.repository

import com.example.newstoday.domain.model.User

interface UserRepository {

    suspend fun getUser(): User
    suspend fun saveUser(user: User)
}