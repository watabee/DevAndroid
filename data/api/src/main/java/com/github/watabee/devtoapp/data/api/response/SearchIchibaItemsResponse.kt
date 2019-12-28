package com.github.watabee.devtoapp.data.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchIchibaItemsResponse(
    val count: Int,
    val page: Int,
    val first: Int,
    val last: Int,
    val pageCount: Int,
    @Json(name = "Items") val items: List<Item>
) {
    @JsonClass(generateAdapter = true)
    data class Item(
        val itemCaption: String,
        val itemCode: String,
        val itemName: String,
        val itemPrice: Int,
        val itemUrl: String,
        val mediumImageUrls: List<String>,
        val reviewAverage: String,
        val reviewCount: Int,
        val shopCode: String,
        val shopName: String,
        val shopUrl: String,
        val smallImageUrls: List<String>,
        val taxFlag: Int
    )
}
