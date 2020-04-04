package com.proj.newsboard.extensions

import com.proj.newsboard.dataClass.Article
import com.proj.newsboard.localArticlesDb.ArticleData

fun List<Article>.toArticleDataList(): List<ArticleData> {
    return this.map {
        ArticleData(
            it.source.name,
            it.title,
            it.description,
            it.url,
            it.urlToImage,
            it.publishedAt
        )
    }
}