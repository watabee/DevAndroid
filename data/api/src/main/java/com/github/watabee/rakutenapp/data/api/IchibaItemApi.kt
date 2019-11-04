package com.github.watabee.rakutenapp.data.api

import com.github.watabee.rakutenapp.data.api.response.FindRankingItemsResponse
import com.github.watabee.rakutenapp.data.api.response.SearchIchibaItemsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IchibaItemApi {

    @GET("IchibaItem/Search/20170706")
    fun searchItems(
        @Query("keyword") keyword: String,
        @Query("hits") hits: Int,
        @Query("page") page: Int
    ): Single<SearchIchibaItemsResponse>

    @GET("IchibaItem/Ranking/20170628")
    fun findRankingItems(@Query("page") page: Int): Single<FindRankingItemsResponse>
}
