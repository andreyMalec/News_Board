package com.proj.newsboard.api

import com.google.gson.Gson
import com.proj.newsboard.dataClass.News
import java.io.IOException
import java.net.URL

class NewsApi(private val apiKey: String): Api {
    private val baseUrl = "http://newsapi.org/v2/"

    @Throws(IOException::class)
    override fun getEverything(request: ApiRequestEverything): List<News> {
        val requestUrl = makeUrlEverything(request)

        return getResponse(requestUrl).articles
    }

    @Throws(IOException::class)
    override fun getTop(request: ApiRequestTop): List<News> {
        val requestUrl = makeUrlTop(request)

        return getResponse(requestUrl).articles
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

        if (request.q != null) requestUrl += "q=" + request.q + "&"
        requestUrl += "page=" + request.page + "&"
        requestUrl += "apiKey=" + apiKey

        return requestUrl
    }

    private fun getResponse(requestUrl: String): APIResponse {
        val stringResponse = URL(requestUrl).readText()

        return Gson().fromJson(stringResponse, APIResponse::class.java)
    }
}