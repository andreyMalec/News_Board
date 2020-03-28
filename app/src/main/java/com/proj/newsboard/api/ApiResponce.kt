package com.proj.newsboard.api

import com.proj.newsboard.dataClass.News

data class APIResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<News>
)