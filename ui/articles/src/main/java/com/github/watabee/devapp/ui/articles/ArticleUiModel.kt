package com.github.watabee.devapp.ui.articles

import com.github.watabee.devapp.data.Article

private const val MAX_TAGS_COUNT = 5

internal data class ArticleUiModel(val article: Article) {
    val id: Int = article.id
    val title: String = article.title
    val userImage: String = article.user.profileImage90
    val username: String = article.user.username
    val readablePublishDate: String = article.readablePublishDate
    val tagList: List<String?> = List(MAX_TAGS_COUNT, article.tagList::getOrNull)
}
