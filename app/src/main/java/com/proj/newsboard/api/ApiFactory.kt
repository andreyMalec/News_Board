package com.proj.newsboard.api

import android.content.Context
import com.proj.newsboard.R

object ApiFactory {
    fun create(fromContext: Context): Api {
        val apiKey = fromContext.getString(R.string.apiKey)
        return NewsApi(apiKey)
    }
}