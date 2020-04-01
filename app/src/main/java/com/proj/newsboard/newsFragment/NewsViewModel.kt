package com.proj.newsboard.newsFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedList.Config
import com.proj.newsboard.api.ApiFactory
import com.proj.newsboard.api.ApiRequest
import com.proj.newsboard.api.ApiRequestEverything
import com.proj.newsboard.api.ApiRequestTop
import com.proj.newsboard.dataClass.Category
import com.proj.newsboard.dataClass.Country
import com.proj.newsboard.dataClass.Language
import com.proj.newsboard.dataClass.SortBy
import com.proj.newsboard.extensions.toArticleDataList
import com.proj.newsboard.localArticlesDb.ArticleData
import com.proj.newsboard.localArticlesDb.ArticlesDatabase
import com.proj.newsboard.util.DateFormatter

class NewsViewModel(app: Application): AndroidViewModel(app) {
    private val articlesMaxSize = 80
    private val articlesPageSize = 20

    private val lastApiRequest: ApiRequest

//    private val dateFormatter = DateFormatter()

    val articles: LiveData<PagedList<ArticleData>>
    private val db: ArticlesDatabase = ArticlesDatabase.getInstance(app.applicationContext)!!
    private val api = ApiFactory.create(app.applicationContext)

    init {
        val config = Config.Builder().setMaxSize(articlesMaxSize).setPageSize(articlesPageSize).build()
        lastApiRequest = ApiRequestTop(country = Country.RussianFederation, category = Category.Technology)
        articles = LivePagedListBuilder(db.newsDataDao().getAll(), config)
            .setBoundaryCallback(ArticleBoundaryCallback(lastApiRequest, api, db))
            .build()

        updateNews()
    }

    fun updateNews() {
        api.getTop(lastApiRequest as ApiRequestTop) { articles ->
            if (articles.isNotEmpty()) {
                db.newsDataDao().deleteAll()
                db.newsDataDao().insertAll(articles.toArticleDataList())
            }
        }
    }
}