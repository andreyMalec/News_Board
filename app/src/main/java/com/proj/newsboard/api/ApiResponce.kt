package com.proj.newsboard.api

import com.proj.newsboard.dataClass.Article

data class APIResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)