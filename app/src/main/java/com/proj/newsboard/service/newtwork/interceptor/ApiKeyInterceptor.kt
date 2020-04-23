package com.proj.newsboard.service.newtwork.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor: Interceptor {
    private val apiKey = "1510c682a5a04c4f8eb696f6394c9844"

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder().apply {
            header("x-api-key", apiKey)
            method(original.method(), original.body())
        }.build()
        return chain.proceed(request)
    }
}