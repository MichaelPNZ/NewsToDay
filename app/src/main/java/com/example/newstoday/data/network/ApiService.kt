package com.example.newstoday.data.network

import retrofit2.http.GET

interface ApiService {

    @GET("everything?q=world&sortBy=popularity")
    suspend fun getCategories(): ArticlesResponse
}