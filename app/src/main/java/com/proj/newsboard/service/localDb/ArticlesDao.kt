package com.proj.newsboard.service.localDb

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import com.proj.newsboard.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {
    @Query("SELECT * from Article")
    fun factory(): DataSource.Factory<Int, Article>

    @Query("SELECT * from Article")
    fun getAll(): Flow<List<Article>>

    @Query("SELECT * FROM Article WHERE title LIKE :searchQuery")
    fun getTitleContains(searchQuery: String): Flow<List<Article>>

    @Insert(onConflict = IGNORE)
    fun insert(article: Article)

    @Insert(onConflict = IGNORE)
    fun insertAll(articles: List<Article>)

    @Query("DELETE from Article")
    fun deleteAll()

    @Transaction
    fun cleanInsertAll(articles: List<Article>) {
        if (articles.isNotEmpty()) {
            deleteAll()
            insertAll(articles)
        }
    }
}