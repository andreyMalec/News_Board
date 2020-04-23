package com.proj.newsboard.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Article(
    val source: String?,
    val title: String,
    val description: String?,
    @PrimaryKey
    val url: String,
    val urlToImage: String?,
    val publishedAt: String?
)