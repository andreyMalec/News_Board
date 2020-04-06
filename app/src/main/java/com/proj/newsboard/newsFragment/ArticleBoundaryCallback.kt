package com.proj.newsboard.newsFragment

import android.util.Log
import androidx.paging.PagedList
import com.proj.newsboard.api.NewsApi
import com.proj.newsboard.extensions.getNews
import com.proj.newsboard.extensions.toArticleDataList
import com.proj.newsboard.localArticlesDb.ArticleData
import com.proj.newsboard.localArticlesDb.ArticlesDatabase
import java.util.concurrent.Executors

class ArticleBoundaryCallback(
    var request: BoundaryCallbackRequest,
    private val api: NewsApi,
    private val db: ArticlesDatabase
): PagedList.BoundaryCallback<ArticleData>() {
    private val executor = Executors.newSingleThreadExecutor()
    private val helper = PagingRequestHelper(executor)

    override fun onZeroItemsLoaded() {
        Log.e("test", "onZeroItemsLoaded: ")
//        Log.e("test", "onZeroItemsLoaded: " + "loading " + (request.request as ApiRequestTop).category)

        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { helperCallback ->
            api.getNews(request.request) {
                Log.e("test", "onZeroItemsLoaded: " + "loaded ${it.size} articles from ${request.request.page} page")
                request.request = request.request.nextPage()
                db.newsDataDao().cleanInsertToDb(it.toArticleDataList())
                helperCallback.recordSuccess()
            }
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: ArticleData) {
//        Log.e("test", "onItemAtEndLoaded: " + "loading " + (request.request as ApiRequestTop).category)
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { helperCallback ->
            api.getNews(request.request) {
                Log.e("test", "getNews:           " + "loaded ${it.size} articles from ${request.request.page} page")
                request.request = request.request.nextPage()
                db.newsDataDao().insertAll(it.toArticleDataList())
                helperCallback.recordSuccess()
            }
        }
    }
}