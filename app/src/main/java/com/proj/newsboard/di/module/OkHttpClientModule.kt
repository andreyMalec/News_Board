package com.proj.newsboard.di.module

import com.proj.newsboard.service.newtwork.interceptor.ApiKeyInterceptor
import com.proj.newsboard.service.newtwork.interceptor.UrlInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class OkHttpClientModule {
    @Provides
    @Singleton
    fun okHttpClient(apiKeyInterceptor: ApiKeyInterceptor, urlInterceptor: UrlInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(urlInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun apiKeyInterceptor() = ApiKeyInterceptor()

    @Provides
    @Singleton
    fun urlInterceptor() = UrlInterceptor()
}