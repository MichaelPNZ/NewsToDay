package com.example.newstoday.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newstoday.data.local.dao.UsersDao
import com.example.newstoday.data.local.entity.StringListConverter
import com.example.newstoday.data.local.entity.UserDBO

@Database(
    entities = [UserDBO::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val usersDao: UsersDao
}