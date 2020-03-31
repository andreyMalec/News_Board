package com.proj.newsboard.localArticlesDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ArticleData::class], version = 1)
abstract class ArticlesDatabase: RoomDatabase() {
    abstract fun newsDataDao(): ArticlesDao

    companion object {
        private var database: ArticlesDatabase? = null

        fun getInstance(context: Context): ArticlesDatabase? {
            if (database == null) {
                synchronized(ArticlesDatabase::class) {
                    database = Room.databaseBuilder(
                        context.applicationContext,
                        ArticlesDatabase::class.java, "articlesDb"
                    )
                        .build()
                }
            }
            return database
        }

        fun destroyInstance() {
            database = null
        }
    }
}