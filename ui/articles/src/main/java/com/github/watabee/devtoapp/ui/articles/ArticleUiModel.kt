package com.github.watabee.devtoapp.ui.articles

import com.github.watabee.devtoapp.data.api.response.Article

internal data class ArticleUiModel(
    val id: Int,
    val title: String,
    val userImage: String,
    val username: String,
    val readablePublishDate: String,
    val tagList: List<String>
) {
    constructor(article: Article) : this(
        article.id,
        article.title,
        article.user.profileImage90,
        article.user.username,
        article.readablePublishDate,
        article.tagList
    )
}
