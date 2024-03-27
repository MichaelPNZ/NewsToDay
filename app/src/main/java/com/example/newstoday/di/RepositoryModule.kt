package com.example.newstoday.di

import com.example.newstoday.data.repository.ArticlesRepositoryImpl
import com.example.newstoday.data.repository.UserRepositoryImpl
import com.example.newstoday.domain.repository.ArticleRepository
import com.example.newstoday.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindArticleRepository(impl: ArticlesRepositoryImpl): ArticleRepository

    @Binds
    @Singleton
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository


}