package com.example.newstoday.data.repository

import com.example.newstoday.data.local.AppDatabase
import com.example.newstoday.data.local.entity.UserDBO
import com.example.newstoday.data.mapper.toUser
import com.example.newstoday.domain.model.User
import com.example.newstoday.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    db: AppDatabase,
) : UserRepository {

    private val userDao = db.usersDao

    override suspend fun getUser(): User {
        return userDao.getUser()?.toUser() ?: User(0, "", "", emptyList())
    }

    override suspend fun saveUser(user: User) {
        userDao.insertUser(UserDBO(user.id, user.email, user.password, user.favoriteCategories))
    }
}