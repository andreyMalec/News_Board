package com.proj.newsboard.newsFragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedList.Config
import com.proj.newsboard.api.ApiFactory
import com.proj.newsboard.api.ApiRequestEverything
import com.proj.newsboard.api.ApiRequestTop
import com.proj.newsboard.dataClass.Category
import com.proj.newsboard.dataClass.Country
import com.proj.newsboard.dataClass.Language
import com.proj.newsboard.dataClass.SortBy
import com.proj.newsboard.localArticlesDb.ArticleData
import com.proj.newsboard.localArticlesDb.ArticlesDatabase

class NewsViewModel(app: Application): AndroidViewModel(app) {
    private val articlesMaxSize = 80
    private val articlesPageSize = 20

//    private val dateFormatter = DateFormatter()

    val articles: LiveData<PagedList<ArticleData>>
    val datePickerVisibility = MutableLiveData(false)
    private val db = ArticlesDatabase.getInstance(app.applicationContext)!!
    private val api = ApiFactory.create(app.applicationContext)
    private val callback: ArticleBoundaryCallback
    private val boundaryCallbackRequest: BoundaryCallbackRequest

    init {
        val config = Config.Builder().apply {
//            setMaxSize(articlesMaxSize)
            setPageSize(articlesPageSize)
            setEnablePlaceholders(false)
            setPrefetchDistance(articlesPageSize / 2)
        }.build()
        val request = ApiRequestTop(country = Country.RussianFederation, category = Category.General)
        boundaryCallbackRequest = BoundaryCallbackRequest(request)
        callback = ArticleBoundaryCallback(boundaryCallbackRequest, api, db)
        articles = LivePagedListBuilder(db.newsDataDao().getAll(), config)
            .setBoundaryCallback(callback)
            .build()
    }

    fun pickDateRange(startDate: String, endDate: String) {
        val bcr = boundaryCallbackRequest.request
        if (bcr is ApiRequestEverything) {
            val request =
                ApiRequestEverything(bcr.q, startDate, endDate, bcr.sources, SortBy.PublishedAt, Language.Russian, 1)

            Log.e("test", "-----------------------------------------")
            Log.e("test", "pickDateRange: " + "   pick from " + startDate + " to " + endDate)

            boundaryCallbackRequest.request = request
            updateNews()
        }
    }

    fun searchNews(query: String) {
        val request = ApiRequestEverything(language = Language.Russian, q = query)

        Log.e("test", "-----------------------------------------")
        Log.e("test", "searchNews: " + "   search " + query)

        boundaryCallbackRequest.request = request
        updateNews()
    }

    fun loadCategory(category: Category) {
        val request = ApiRequestTop(country = Country.RussianFederation, category = category)

        Log.e("test", "-----------------------------------------")
        Log.e("test", "loadCategory: " + "     load " + category)

        boundaryCallbackRequest.request = request
        updateNews()
    }

    fun updateNews() {
        datePickerVisibility.value = boundaryCallbackRequest.request is ApiRequestEverything

        boundaryCallbackRequest.request = boundaryCallbackRequest.request.firstPage()
        callback.onZeroItemsLoaded()
    }
}