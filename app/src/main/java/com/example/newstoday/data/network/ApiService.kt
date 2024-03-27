package com.example.newstoday.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything?q=world&sortBy=popularity")
    suspend fun getCategories(): ArticlesResponse

    @GET("everything")
    suspend fun getCategory(@Query("q") category: String): ArticlesResponse

    @GET("everything")
    suspend fun getSelectedCategories(
        @Query("q") categories: List<String>,
        @Query("sortBy") sortBy: String = "popularity"
    ): ArticlesResponse

    @GET("everything")
    suspend fun getFavoriteCategories(
        @Query("q") categories: List<String>,
        @Query("sortBy") sortBy: String = "popularity"
    ): ArticlesResponse
}