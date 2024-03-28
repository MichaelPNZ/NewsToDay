package com.example.newstoday.data.mapper


import com.example.newstoday.data.local.entity.ArticleDBO
import com.example.newstoday.data.network.ArticleDTO
import com.example.newstoday.domain.model.Article
import com.example.newstoday.domain.model.Source

fun ArticleDTO.toArticle() = Article(
    source = Source(id = source?.id ?: "", name = source?.name ?: ""),
    author = author ?: "",
    title = title ?: "",
    description = description ?: "",
    url = url ?: "",
    urlToImage = urlToImage ?: "",
    publishedAt = publishedAt ?: "",
    content = content ?: "",
)

fun ArticleDBO.toArticle() = Article(
    source = Source(id = source.id, name = source.name),
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content,
)