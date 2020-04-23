package com.proj.newsboard.repo

import com.proj.newsboard.model.Article
import com.proj.newsboard.service.localDb.ArticlesDao
import com.proj.newsboard.service.newtwork.api.NewsApi
import com.proj.newsboard.service.newtwork.Result
import com.proj.newsboard.service.newtwork.apiCall
import com.proj.newsboard.service.newtwork.request.ApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NewsRepo(private val api: NewsApi, private val dao: ArticlesDao) {
    suspend fun loadNews(request: ApiRequest): Result<List<Article>> {
        return apiCall {
            val articles = if (request.q.isNullOrBlank())
                api.getTop(request.toMap()).convert()
            else
                api.getEverything(request.toMap()).convert()

            if (request.page != 1)
                cacheArticles(articles)
            else
                cleanCacheArticles(articles)
            articles
        }
    }

    private suspend fun cacheArticles(articles: List<Article>) {
        withContext(Dispatchers.IO) {
            dao.insertAll(articles)
        }
    }

    private suspend fun cleanCacheArticles(articles: List<Article>) {
        withContext(Dispatchers.IO) {
            dao.cleanInsertAll(articles)
        }
    }

    fun articlesFactory() = dao.factory()

    fun getAll(): Flow<List<Article>> = dao.getAll()

    fun getTitleContains(query: String): Flow<List<Article>> {
        val formattedQuery = "%" + query.trim().toLowerCase() + "%"
        return dao.getTitleContains(formattedQuery)
    }
}