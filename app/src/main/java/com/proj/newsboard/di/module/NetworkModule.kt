package com.proj.newsboard.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.proj.newsboard.service.newtwork.api.NewsApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [OkHttpClientModule::class])
class NetworkModule {
    @Provides
    @Singleton
    fun newsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl("https://newsapi.org/v2/")
            addConverterFactory(gsonConverterFactory)
            client(okHttpClient)
        }.build()
    }

    @Provides
    @Singleton
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)
}