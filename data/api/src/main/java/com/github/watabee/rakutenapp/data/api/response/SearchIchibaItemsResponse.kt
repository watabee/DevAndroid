package com.github.watabee.rakutenapp.data.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchIchibaItemsResponse(
    val count: Int,
    val page: Int,
    val first: Int,
    val last: Int,
    val pageCount: Int,
    @Json(name = "Items") val items: List<IchibaItem>
)