package com.github.watabee.devtoapp.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.watabee.devtoapp.data.Article
import java.util.Date

@Entity(
    tableName = "article_details"
)
data class ArticleDetailEntity(
    @PrimaryKey override val id: Int,
    @ColumnInfo(name = "type_of") override val typeOf: String,
    override val title: String,
    override val description: String,
    @ColumnInfo(name = "cover_image") override val coverImage: String?,
    @ColumnInfo(name = "readable_publish_date") override val readablePublishDate: String,
    @ColumnInfo(name = "tags") override val tags: String,
    override val url: String,
    @ColumnInfo(name = "body_markdown") override val bodyMarkdown: String,
    @ColumnInfo(name = "comments_count") override val commentsCount: Int,
    @ColumnInfo(name = "positive_reactions_count") override val positiveReactionsCount: Int,
    @ColumnInfo(name = "published_at") override val publishedAt: Date,
    @Embedded override val user: ArticleUserEntity
) : Article {
    override val tagList: List<String> get() = tags.split(", ")

    constructor(article: Article) : this(
        id = article.id, typeOf = article.typeOf, title = article.title, description = article.description,
        coverImage = article.coverImage, readablePublishDate = article.readablePublishDate, tags = article.tags,
        url = article.url, bodyMarkdown = article.bodyMarkdown, commentsCount = article.commentsCount,
        positiveReactionsCount = article.positiveReactionsCount, publishedAt = article.publishedAt,
        user = ArticleUserEntity(article.user)
    )
}
