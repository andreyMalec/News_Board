package com.proj.newsboard.api

import com.proj.newsboard.dataClass.Article

interface Api {
    fun getEverything(request: ApiRequestEverything, onDataReceived: (data: List<Article>) -> Unit)

    fun getTop(request: ApiRequestTop, onDataReceived: (data: List<Article>) -> Unit)
}