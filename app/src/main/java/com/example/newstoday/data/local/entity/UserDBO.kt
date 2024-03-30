package com.example.newstoday.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.newstoday.domain.model.Article
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity
data class UserDBO(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("email") val email: String,
    @ColumnInfo("password") val password: String,
    @ColumnInfo("favoriteCategories") val favoriteCategories: List<String>,
    @ColumnInfo("articles") val articles: List<Article>,
    @ColumnInfo("isLogin") val isLogin: Boolean,
    )

class StringListConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromArticles(value: String): List<Article> {
        val listType = object : TypeToken<List<Article>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toArticles(list: List<Article>): String {
        return Gson().toJson(list)
    }
}