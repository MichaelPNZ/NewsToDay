package com.example.newstoday.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newstoday.data.local.entity.UserDBO

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDBO)

    @Query("SELECT * FROM userdbo WHERE id = :id")
    suspend fun getUserById(id: Int): UserDBO?

    @Query("SELECT * FROM userdbo WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserDBO?

    @Query("SELECT * FROM userdbo WHERE isLogin = 1")
    suspend fun getIsLoginUser(): UserDBO?

    @Query("SELECT * FROM userdbo")
    suspend fun getAllUser(): List<UserDBO>?
}