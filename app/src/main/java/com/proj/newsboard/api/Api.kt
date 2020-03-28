package com.proj.newsboard.api

import com.proj.newsboard.dataClass.News

interface Api {
    fun getEverything(request: ApiRequestEverything): List<News>

    fun getTop(request: ApiRequestTop): List<News>
}