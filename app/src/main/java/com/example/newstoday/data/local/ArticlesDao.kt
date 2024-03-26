package com.example.newstoday.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticles(result: ArticleDBO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(result: ArticleDBO)

    @Query("SELECT * FROM articledbo")
    suspend fun getArticles(): List<ArticleDBO>

    @Query("SELECT * FROM articledbo WHERE isFavorite = 1")
    fun getFavoriteArticles(): Flow<ArticleDBO?>
}