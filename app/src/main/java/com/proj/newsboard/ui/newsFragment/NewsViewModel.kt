package com.proj.newsboard.ui.newsFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedList.Config
import com.proj.newsboard.model.*
import com.proj.newsboard.repo.NewsRepo
import com.proj.newsboard.service.newtwork.request.ApiRequestEverything
import com.proj.newsboard.service.newtwork.request.ApiRequestTop
import javax.inject.Inject

class NewsViewModel @Inject constructor(private val newsRepo: NewsRepo): ViewModel() {
    private val articlesMaxSize = 80
    private val articlesPageSize = 20

    val articles: LiveData<PagedList<Article>>
    val datePickerVisibility = MutableLiveData(false)

    private val callback: ArticleBoundaryCallback
    private val boundaryCallbackRequest: BoundaryCallbackRequest

    init {
        val config = Config.Builder().apply {
            setMaxSize(articlesMaxSize)
            setPageSize(articlesPageSize)
            setEnablePlaceholders(false)
            setPrefetchDistance(articlesPageSize / 2)
        }.build()
        val request = ApiRequestTop(
            country = Country.RussianFederation,
            category = Category.General
        )
        boundaryCallbackRequest = BoundaryCallbackRequest(request)
        callback = ArticleBoundaryCallback(boundaryCallbackRequest, newsRepo, viewModelScope)
        articles = LivePagedListBuilder(newsRepo.articlesFactory(), config)
            .setBoundaryCallback(callback)
            .build()
    }

    fun pickDateRange(startDate: String, endDate: String) {
        val bcr = boundaryCallbackRequest.request
        if (bcr is ApiRequestEverything) {
            val request =
                ApiRequestEverything(
                    bcr.q,
                    startDate,
                    endDate,
                    bcr.sources,
                    SortBy.PublishedAt,
                    Language.Russian,
                    1
                )

            boundaryCallbackRequest.request = request
            updateNews()
        }
    }

    fun searchNews(query: String) {
        val request = ApiRequestEverything(
            language = Language.Russian,
            q = query
        )

        boundaryCallbackRequest.request = request
        updateNews()
    }

    fun loadCategory(category: Category) {
        val request = ApiRequestTop(
            country = Country.RussianFederation,
            category = category
        )

        boundaryCallbackRequest.request = request
        updateNews()
    }

    fun updateNews() {
        datePickerVisibility.value = boundaryCallbackRequest.request is ApiRequestEverything

        boundaryCallbackRequest.request = boundaryCallbackRequest.request.firstPage()
        callback.onZeroItemsLoaded()
    }
}