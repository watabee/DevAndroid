package com.github.watabee.devapp.ui.article

import com.github.watabee.devapp.data.Article
import com.github.watabee.devapp.extensions.toCommaString

private const val MAX_TAGS_COUNT = 5

data class ArticleDetailUiModel(
    val id: Int,
    val title: String,
    val username: String,
    val userImage: String,
    val readablePublishDate: String,
    val tagList: List<String?>,
    val coverImage: String?,
    val description: String,
    val positiveReactionsCountText: String,
    val commentsCountText: String,
    val bodyMarkdownText: String
) {
    constructor(article: Article) : this(
        id = article.id, title = article.title, username = article.user.username, userImage = article.user.profileImage90,
        readablePublishDate = article.readablePublishDate, tagList = List(MAX_TAGS_COUNT, article.tagList::getOrNull),
        coverImage = article.coverImage, description = article.description,
        positiveReactionsCountText = article.positiveReactionsCount.toCommaString(),
        commentsCountText = article.commentsCount.toCommaString(),
        bodyMarkdownText = article.bodyMarkdown
            .replaceFirst("""---\ntitle:.*?---""".toRegex(RegexOption.DOT_MATCHES_ALL), "") // Delete metadata text
    )
}
