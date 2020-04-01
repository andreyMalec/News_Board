package com.proj.newsboard.newsFragment

import android.util.Log
import androidx.paging.PagedList
import com.proj.newsboard.api.Api
import com.proj.newsboard.api.ApiRequest
import com.proj.newsboard.api.ApiRequestEverything
import com.proj.newsboard.api.ApiRequestTop
import com.proj.newsboard.extensions.toArticleDataList
import com.proj.newsboard.localArticlesDb.ArticleData
import com.proj.newsboard.localArticlesDb.ArticlesDatabase

class ArticleBoundaryCallback(request: ApiRequest, private val api: Api, private val db: ArticlesDatabase): PagedList.BoundaryCallback<ArticleData>() {
    private var endOfTop = false
    private var endOfEverything = false
    private var requestTop = if (request is ApiRequestTop) request else null
    private var requestEverything = if (request is ApiRequestEverything) request else null

    override fun onZeroItemsLoaded() {

    }

    override fun onItemAtEndLoaded(itemAtEnd: ArticleData) {
        if (requestTop != null) {
            if (endOfTop) return
            requestTop = requestTop!!.nextPage()
            api.getTop(requestTop!!) {
                if (it.size < 20) endOfTop = true
                db.newsDataDao().insertAll(it.toArticleDataList())
            }
        } else if (requestEverything != null) {
            if (endOfEverything) return
            requestEverything = requestEverything!!.nextPage()
            api.getEverything(requestEverything!!.nextPage()) {
                if (it.size < 20) endOfEverything = true
                db.newsDataDao().insertAll(it.toArticleDataList())
            }
        }
    }
}