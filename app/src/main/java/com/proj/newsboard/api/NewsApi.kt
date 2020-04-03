package com.proj.newsboard.api

import android.net.Uri
import com.google.gson.Gson
import com.proj.newsboard.dataClass.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URI
import java.net.URL
import java.net.URLEncoder

class NewsApi(private val apiKey: String): Api {
    private val baseUrl = "https://newsapi.org/v2/"

    @Throws(IOException::class)
    override fun getEverything(request: ApiRequestEverything, onDataReceived: (data: List<Article>) -> Unit) {
        makeRequest(makeUrlEverything(request), onDataReceived)
    }

    @Throws(IOException::class)
    override fun getTop(request: ApiRequestTop, onDataReceived: (data: List<Article>) -> Unit) {
        makeRequest(makeUrlTop(request), onDataReceived)
    }

    @Throws(IOException::class)
    override fun getNews(request: ApiRequest, onDataReceived: (data: List<Article>) -> Unit) {
        val requestUrl = if (request is ApiRequestTop) makeUrlTop(request)
        else makeUrlEverything(request as ApiRequestEverything)

        makeRequest(requestUrl, onDataReceived)
    }

    private fun makeRequest(requestUrl: String, onDataReceived: (data: List<Article>) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.Default) {
                getResponse(requestUrl)
            }

            withContext(Dispatchers.IO) {
                onDataReceived(response.articles)
            }
        }
    }

    private fun makeUrlEverything(request: ApiRequestEverything): String {
        var requestUrl = baseUrl + "everything?"

        if (request.from != null) requestUrl += "from=" + request.from + "&"
        if (request.to != null) requestUrl += "to=" + request.to + "&"
        if (request.language != null) requestUrl += "language=" + request.language.value + "&"
        if (request.sources != null) requestUrl += "sources=" + request.sources + "&"
        if (request.sortBy != null) requestUrl += "sortBy=" + request.sortBy.value + "&"

        return requestUrl + prepareUrl(request)
    }

    private fun makeUrlTop(request: ApiRequestTop): String {
        var requestUrl = baseUrl + "top-headlines?"

        if (request.country != null) requestUrl += "country=" + request.country.value + "&"
        if (request.sources != null) requestUrl += "sources=" + request.sources + "&"
        if (request.category != null) requestUrl += "category=" + request.category.value + "&"

        return requestUrl + prepareUrl(request)
    }

    private fun prepareUrl(request: ApiRequest): String {
        var requestUrl = ""

        if (request.q != null) requestUrl += "q=" + URLEncoder.encode(request.q, "utf-8") + "&"
        requestUrl += "page=" + request.page + "&"
        requestUrl += "apiKey=" + apiKey

        return requestUrl
    }

    private fun getResponse(requestUrl: String): APIResponse {
        return try {
            val stringResponse = URL(requestUrl).readText()

            Gson().fromJson(stringResponse, APIResponse::class.java)
        } catch (e: Exception) {
            APIResponse(e.message.toString(), 0, listOf())
        }
    }
}