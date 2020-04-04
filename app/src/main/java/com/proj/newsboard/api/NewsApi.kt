package com.proj.newsboard.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsApi {
    @GET("everything?")
    fun getEverything(@QueryMap options: Map<String, String?>): Call<APIResponse>

    @GET("top-headlines?")
    fun getTop(@QueryMap options: Map<String, String?>): Call<APIResponse>
}