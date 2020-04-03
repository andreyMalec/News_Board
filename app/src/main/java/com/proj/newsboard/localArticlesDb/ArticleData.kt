package com.proj.newsboard.localArticlesDb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleData(
    val source: String?,
    val title: String,
    val description: String?,
    @PrimaryKey
    val url: String,
    val urlToImage: String?,
    val publishedAt: String?
)