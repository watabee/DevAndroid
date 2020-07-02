package com.github.watabee.devapp.data.api

import com.github.watabee.devapp.data.api.response.Article
import com.github.watabee.devapp.data.api.response.ArticleDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DevApi {

    @GET("articles")
    suspend fun findArticles(
        @Query("page") page: Int? = null,
        @Query("per_page") perPage: Int? = null,
        @Query("tag") tag: String? = null
    ): List<Article>

    @GET("articles/{id}")
    suspend fun findArticle(@Path("id") articleId: Int): ArticleDetail
}
