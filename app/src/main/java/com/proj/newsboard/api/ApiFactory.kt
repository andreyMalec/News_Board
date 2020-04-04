package com.proj.newsboard.api

import android.content.Context
import com.proj.newsboard.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    fun create(fromContext: Context): NewsApi {
        val apiKey = fromContext.getString(R.string.apiKey)

        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val originalUrl = original.url()
            val query = originalUrl.encodedQuery()
            val request = original.newBuilder()
                .header("x-api-key", apiKey)
                .method(original.method(), original.body())
                .url(originalUrl.newBuilder().encodedQuery(query?.drop(1)).build())
            chain.proceed(request.build())
        }.build()

        val retrofit = Retrofit.Builder().apply {
            baseUrl("https://newsapi.org/v2/")
            client(client)
            addConverterFactory(GsonConverterFactory.create())
        }.build()

        return retrofit.create(NewsApi::class.java)
    }
}