package com.github.watabee.devtoapp.data.api.response

import com.github.watabee.devtoapp.data.ArticleUser
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleUser(
    @Json(name = "name") override val name: String,
    @Json(name = "username") override val username: String,
    @Json(name = "profile_image") override val profileImage: String,
    @Json(name = "profile_image_90") override val profileImage90: String
) : ArticleUser {
    companion object {
        const val serialVersionUID = 1L
    }
}
