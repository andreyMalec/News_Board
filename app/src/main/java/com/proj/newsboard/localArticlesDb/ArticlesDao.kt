package com.proj.newsboard.localArticlesDb

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query

@Dao
interface ArticlesDao {
    @Query("SELECT * from ArticleData")
    fun getAll(): DataSource.Factory<Int, ArticleData>

    @Insert(onConflict = IGNORE)
    fun insert(articleData: ArticleData)

    @Insert(onConflict = IGNORE)
    fun insertAll(articles: List<ArticleData>)

    @Query("DELETE from ArticleData")
    fun deleteAll()
}