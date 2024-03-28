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

    override suspend fun getUserById(id: Int): User {
        return userDao.getUserById(id)?.toUser()
            ?: User(
                1,
                "123@123",
                "123",
                listOf("Random"),
                emptyList()
            )
    }

    override suspend fun saveUser(user: User) {
        userDao.insertUser(
            UserDBO(
                user.id,
                user.email,
                user.password,
                user.favoriteCategories,
                user.articles
            ))
    }

    override suspend fun getUserByEmail(email: String): User {
        return userDao.getUserByEmail(email)?.toUser() ?: User(1, "", "", emptyList(), emptyList())
    }

    override suspend fun getAllUser(): List<User> {
        return userDao.getAllUser()?.map { it.toUser() } ?: emptyList()
    }
}