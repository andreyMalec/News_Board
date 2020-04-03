package com.proj.newsboard.newsFragment

import android.util.Log
import androidx.paging.PagedList
import com.proj.newsboard.api.Api
import com.proj.newsboard.api.ApiRequest
import com.proj.newsboard.api.ApiRequestEverything
import com.proj.newsboard.api.ApiRequestTop
import com.proj.newsboard.dataClass.Article
import com.proj.newsboard.extensions.toArticleDataList
import com.proj.newsboard.localArticlesDb.ArticleData
import com.proj.newsboard.localArticlesDb.ArticlesDatabase
import java.util.concurrent.Executors

class ArticleBoundaryCallback(private val request: ApiRequest, private val api: Api, private val db: ArticlesDatabase): PagedList.BoundaryCallback<ArticleData>() {
    private var endOfResponse = false
    private var requestPaged = request

    private val executor = Executors.newSingleThreadExecutor()
    private val helper = PagingRequestHelper(executor)

    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { helperCallback ->
            api.getNews(request) {
                endOfResponse = it.size < 20
                db.newsDataDao().cleanInsertToDb(it.toArticleDataList())
                helperCallback.recordSuccess()
            }
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: ArticleData) {
        if (!endOfResponse)
            helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { helperCallback ->
                requestPaged = requestPaged.nextPage()
                api.getNews(requestPaged) {
                    endOfResponse = it.size < 20
                    db.newsDataDao().insertToDb(it.toArticleDataList())
                    helperCallback.recordSuccess()
                }
            }
    }
}