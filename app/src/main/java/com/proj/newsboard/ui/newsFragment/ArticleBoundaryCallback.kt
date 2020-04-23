package com.proj.newsboard.ui.newsFragment

import android.util.Log
import androidx.paging.PagedList
import com.proj.newsboard.service.newtwork.api.NewsApi
import com.proj.newsboard.model.Article
import com.proj.newsboard.repo.NewsRepo
import com.proj.newsboard.service.localDb.ArticlesDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class ArticleBoundaryCallback(
    var request: BoundaryCallbackRequest,
    private val newsRepo: NewsRepo,
    private val scope: CoroutineScope
): PagedList.BoundaryCallback<Article>() {

    override fun onZeroItemsLoaded() {
        Log.e("test", "onZeroItemsLoaded: ")
        scope.launch(Dispatchers.IO) {
            newsRepo.loadNews(request.request)
            request.request = request.request.nextPage()
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Article) {
        scope.launch(Dispatchers.IO) {
            newsRepo.loadNews(request.request)
            request.request = request.request.nextPage()
        }
    }
}