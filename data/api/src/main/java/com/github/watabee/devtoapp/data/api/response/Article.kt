package com.github.watabee.devtoapp.data.api.response

import com.github.watabee.devtoapp.data.Article
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class Article(
    @Json(name = "type_of") override val typeOf: String,
    @Json(name = "id") override val id: Int,
    @Json(name = "title") override val title: String,
    @Json(name = "description") override val description: String,
    @Json(name = "cover_image") override val coverImage: String?,
    @Json(name = "readable_publish_date") override val readablePublishDate: String,
    @Json(name = "tag_list") override val tagList: List<String>,
    @Json(name = "url") override val url: String,
    @Json(name = "comments_count") override val commentsCount: Int,
    @Json(name = "positive_reactions_count") override val positiveReactionsCount: Int,
    @Json(name = "published_at") override val publishedAt: Date,
    @Json(name = "user") override val user: ArticleUser
) : Article {
    override val tags: String = tagList.joinToString(", ")

    override val bodyMarkdown: String = ""

    companion object {
        const val serialVersionUID = 1L
    }
}
