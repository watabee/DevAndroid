package com.github.watabee.rakutenapp.data.api

import com.github.watabee.rakutenapp.data.api.response.FindRankingItemsResponse
import com.github.watabee.rakutenapp.data.api.response.SearchIchibaItemsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IchibaItemApi {

    @GET("IchibaItem/Search/20170706")
    suspend fun searchItems(
        @Query("keyword") keyword: String,
        @Query("hits") hits: Int,
        @Query("page") page: Int
    ): SearchIchibaItemsResponse

    @GET("IchibaItem/Ranking/20170628")
    suspend fun findRankingItems(@Query("page") page: Int): FindRankingItemsResponse
}
