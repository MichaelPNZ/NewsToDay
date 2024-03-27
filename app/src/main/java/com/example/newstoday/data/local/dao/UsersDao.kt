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

    @Query("SELECT * FROM userdbo")
    suspend fun getUser(): UserDBO?
}