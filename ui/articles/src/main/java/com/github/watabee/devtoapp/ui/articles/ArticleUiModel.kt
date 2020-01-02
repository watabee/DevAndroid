package com.github.watabee.devtoapp.ui.articles

import com.github.watabee.devtoapp.data.Article

private const val MAX_TAGS_COUNT = 5

internal data class ArticleUiModel(
    val id: Int,
    val title: String,
    val userImage: String,
    val username: String,
    val readablePublishDate: String,
    val tagList: List<String?>
) {
    constructor(article: Article) : this(
        article.id,
        article.title,
        article.user.profileImage90,
        article.user.username,
        article.readablePublishDate,
        List(MAX_TAGS_COUNT, article.tagList::getOrNull)
    )
}
