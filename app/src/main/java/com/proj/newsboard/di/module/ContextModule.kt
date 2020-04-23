package com.proj.newsboard.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val app: Application) {
    @Provides
    @Singleton
    fun context(): Context = app.applicationContext
}