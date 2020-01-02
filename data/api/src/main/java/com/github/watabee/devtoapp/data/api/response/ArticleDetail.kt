package com.github.watabee.devtoapp.data.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class ArticleDetail(
    @Json(name = "type_of") val typeOf: String,
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "cover_image") val coverImage: String?,
    @Json(name = "readable_publish_date") val readablePublishDate: String,
    @Json(name = "tags") val tagList: List<String>,
    @Json(name = "tag_list") val tags: String,
    @Json(name = "url") val url: String,
    @Json(name = "body_markdown") val bodyMarkdown: String,
    @Json(name = "comments_count") val commentsCount: Int,
    @Json(name = "positive_reactions_count") val positiveReactionsCount: Int,
    @Json(name = "published_at") val publishedAt: Date,
    @Json(name = "user") val user: ArticleUser
)
