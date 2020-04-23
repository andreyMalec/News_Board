package com.proj.newsboard.di

import com.proj.newsboard.di.activity.ActivityModule
import com.proj.newsboard.di.viewModel.ViewModelModule
import com.proj.newsboard.repo.NewsRepo
import com.proj.newsboard.service.localDb.ArticlesDao
import com.proj.newsboard.service.newtwork.api.NewsApi
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module(includes = [ActivityModule::class, ViewModelModule::class])
class AppModule {
    @Provides
    @Singleton
    fun newsRepo(api: NewsApi, dao: ArticlesDao): NewsRepo = NewsRepo(api, dao)
}