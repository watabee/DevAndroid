package com.github.watabee.devtoapp.data.api

import com.github.watabee.devtoapp.data.api.response.Article
import retrofit2.http.GET
import retrofit2.http.Query

interface DevToApi {

    @GET("articles")
    suspend fun findArticles(
        @Query("page") page: Int? = null,
        @Query("per_page") perPage: Int? = null,
        @Query("tag") tag: String? = null
    ): List<Article>
}
