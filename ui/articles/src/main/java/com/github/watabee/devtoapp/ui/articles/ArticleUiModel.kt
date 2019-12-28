package com.github.watabee.devtoapp.ui.articles

internal data class ArticleUiModel(
    val id: Int,
    val title: String,
    val userImage: String,
    val username: String,
    val readablePublishDate: String,
    val tagList: List<String>
)
