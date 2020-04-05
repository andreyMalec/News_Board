package com.proj.newsboard.extensions

import android.util.Log
import com.proj.newsboard.api.APIResponse
import com.proj.newsboard.api.ApiRequest
import com.proj.newsboard.api.ApiRequestTop
import com.proj.newsboard.api.NewsApi
import com.proj.newsboard.dataClass.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun NewsApi.getNews(request: ApiRequest, onDataReceived: (data: List<Article>) -> Unit) {
    val callback = object: Callback<APIResponse> {
        override fun onFailure(call: Call<APIResponse>, t: Throwable) {
            Log.e("NewsApi", "getNews: ", t)
        }

        override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
            GlobalScope.launch {
                withContext(Dispatchers.IO) {
                    onDataReceived(response.body()?.articles ?: listOf())
                }
            }
        }
    }

    if (request is ApiRequestTop) {
        getTop(request.toMap()).enqueue(callback)
    } else {
        getEverything(request.toMap()).enqueue(callback)
    }
}