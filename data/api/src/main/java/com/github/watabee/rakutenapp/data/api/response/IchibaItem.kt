package com.github.watabee.rakutenapp.data.api.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IchibaItem(
    val itemCaption: String,
    val itemCode: String,
    val itemName: String,
    val itemPrice: String,
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