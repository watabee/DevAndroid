package com.github.watabee.devtoapp.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "article_details"
)
data class ArticleDetailEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "type_of") val typeOf: String,
    val title: String,
    val description: String,
    @ColumnInfo(name = "cover_image") val coverImage: String?,
    @ColumnInfo(name = "readable_publish_date") val readablePublishDate: String,
    @ColumnInfo(name = "tags") val tags: List<String>,
    val url: String,
    @ColumnInfo(name = "body_markdown") val bodyMarkdown: String,
    @ColumnInfo(name = "comments_count") val commentsCount: Int,
    @ColumnInfo(name = "positive_reactions_count") val positiveReactionsCount: Int,
    @ColumnInfo(name = "published_at") val publishedAt: Date,
    @Embedded val user: ArticleUserEntity
)