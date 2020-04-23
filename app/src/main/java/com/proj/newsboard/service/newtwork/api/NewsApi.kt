package com.proj.newsboard.service.newtwork.api

import com.proj.newsboard.service.newtwork.response.APIResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsApi {
    @GET("everything?")
    suspend fun getEverything(@QueryMap options: Map<String, String?>): APIResponse

    @GET("top-headlines?")
    suspend fun getTop(@QueryMap options: Map<String, String?>): APIResponse
}