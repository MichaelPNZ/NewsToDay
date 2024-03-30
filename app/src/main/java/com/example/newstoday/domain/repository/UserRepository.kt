package com.example.newstoday.domain.repository

import com.example.newstoday.domain.model.User

interface UserRepository {

    suspend fun getUserById(id: Int): User
    suspend fun saveUser(user: User)
    suspend fun getUserByEmail(email: String): User
    suspend fun getIsLoginUser(): User
    suspend fun getAllUser(): List<User>
}