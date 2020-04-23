package com.proj.newsboard.service.localDb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.proj.newsboard.model.Article

@Database(entities = [Article::class], version = 1)
abstract class ArticlesDatabase: RoomDatabase() {
    abstract fun newsDataDao(): ArticlesDao
}