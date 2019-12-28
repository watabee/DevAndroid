package com.github.watabee.devtoapp.data.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleUser(
    @Json(name = "name") val name: String,
    @Json(name = "username") val username: String,
    @Json(name = "profile_image") val profileImage: String,
    @Json(name = "profile_image_90") val profileImage90: String
)
