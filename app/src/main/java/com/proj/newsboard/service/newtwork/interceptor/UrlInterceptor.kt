package com.proj.newsboard.service.newtwork.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class UrlInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url()
        val query = originalUrl.encodedQuery()
        val request = original.newBuilder().apply {
            url(originalUrl.newBuilder().encodedQuery(query?.drop(1)).build())
            method(original.method(), original.body())
        }.build()
        return chain.proceed(request)
    }
}