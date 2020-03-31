package com.proj.newsboard.newsFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedList.Config
import com.proj.newsboard.api.ApiFactory
import com.proj.newsboard.api.ApiRequestTop
import com.proj.newsboard.dataClass.Country
import com.proj.newsboard.localArticlesDb.ArticleData
import com.proj.newsboard.localArticlesDb.ArticlesDatabase

class NewsViewModel(private val app: Application): AndroidViewModel(app) {
    private val articlesMaxSize = 80
    private val articlesPageSize = 20

    val articles: LiveData<PagedList<ArticleData>>
    private val db: ArticlesDatabase = ArticlesDatabase.getInstance(app.applicationContext)!!

    init {
        val config = Config.Builder().setMaxSize(articlesMaxSize).setPageSize(articlesPageSize).build()
        articles = LivePagedListBuilder(db.newsDataDao().getAll(), config).build()

        updateNews()
    }

    fun updateNews() {
        ApiFactory.create(app.applicationContext).getTop(
            ApiRequestTop(country = Country.RussianFederation)
        ) { articles ->
            db.newsDataDao().deleteAll()
            db.newsDataDao().insertAll(articles.map {
                ArticleData(
                    it.source.name,
                    it.title,
                    it.description,
                    it.url,
                    it.urlToImage,
                    it.publishedAt
                )
            })
        }
    }
}