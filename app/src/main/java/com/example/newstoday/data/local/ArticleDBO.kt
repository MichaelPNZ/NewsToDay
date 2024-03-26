package com.example.newstoday.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.newstoday.domain.model.Source
import com.google.gson.Gson

@Entity
data class ArticleDBO(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo("source") val source: Source,
    @ColumnInfo("author") val author: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("url") val url: String,
    @ColumnInfo("urlToImage") val urlToImage: String,
    @ColumnInfo("publishedAt") val publishedAt: String,
    @ColumnInfo("content") val content: String,
    @ColumnInfo("isFavorite") val isFavorite: Boolean = false,
)

class Converters {
    @TypeConverter
    fun fromSource(source: Source): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun toSource(sourceString: String): Source {
        return Gson().fromJson(sourceString, Source::class.java)
    }
}