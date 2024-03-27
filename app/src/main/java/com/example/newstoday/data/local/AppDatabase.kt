package com.example.newstoday.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newstoday.data.local.dao.ArticlesDao
import com.example.newstoday.data.local.dao.UsersDao
import com.example.newstoday.data.local.entity.ArticleDBO
import com.example.newstoday.data.local.entity.Converters
import com.example.newstoday.data.local.entity.StringListConverter
import com.example.newstoday.data.local.entity.UserDBO

@Database(
    entities = [ArticleDBO::class, UserDBO::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class, StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val articlesDao: ArticlesDao
    abstract val usersDao: UsersDao
}