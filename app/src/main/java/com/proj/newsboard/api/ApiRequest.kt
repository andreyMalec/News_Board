package com.proj.newsboard.api

import com.proj.newsboard.dataClass.Category
import com.proj.newsboard.dataClass.Country
import com.proj.newsboard.dataClass.Language
import com.proj.newsboard.dataClass.SortBy

interface ApiRequest {
    val q: String?
    val sources: String?
    val page: Int

    fun nextPage(): ApiRequest
}

data class ApiRequestTop(
    override val q: String? = null,
    /** @Note: you can't mix this param with the [country] or [category] params. */
    override val sources: String? = null,
    /** @Note: you can't mix this param with the [sources] param. */
    val category: Category? = null,
    /** @Note: you can't mix this param with the [sources] param. */
    val country: Country? = null,
    override val page: Int = 1
): ApiRequest {
    override fun nextPage(): ApiRequestTop {
        return ApiRequestTop(q, sources, category, country, page + 1)
    }
}

data class ApiRequestEverything(
    override val q: String? = null,
    /** This should be in ISO 8601 format (e.g. 2020-03-28 or 2020-03-28T14:21:35) */
    val from: String? = null,
    /** This should be in ISO 8601 format (e.g. 2020-03-28 or 2020-03-28T14:21:35) */
    val to: String? = null,
    override val sources: String? = null,
    /** @Default: publishedAt */
    val sortBy: SortBy? = null,
    /** @Default: all languages returned. */
    val language: Language? = null,
    override val page: Int = 1
): ApiRequest {
    override fun nextPage(): ApiRequestEverything {
        return ApiRequestEverything(q, from, to, sources, sortBy, language, page + 1)
    }
}