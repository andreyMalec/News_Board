package com.proj.newsboard.di.module

import android.content.Context
import androidx.room.Room
import com.proj.newsboard.service.localDb.ArticlesDao
import com.proj.newsboard.service.localDb.ArticlesDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module(includes = [ContextModule::class])
class DbModule {
    private lateinit var database: ArticlesDatabase

    @Provides
    @Singleton
    fun instance(context: Context): ArticlesDatabase {
        if (this::database.isInitialized.not()) {
            synchronized(this) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    ArticlesDatabase::class.java, "articlesDb"
                ).build()
            }
        }
        return database
    }

    @Provides
    @Singleton
    fun memesDao(database: ArticlesDatabase): ArticlesDao =
        database.newsDataDao()
}