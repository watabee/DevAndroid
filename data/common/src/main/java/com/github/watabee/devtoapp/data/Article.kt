package com.github.watabee.devtoapp.data

import java.io.Serializable
import java.util.Date

interface Article : Serializable {
    val typeOf: String
    val id: Int
    val title: String
    val description: String
    val coverImage: String?
    val readablePublishDate: String
    val tagList: List<String>
    val tags: String
    val url: String
    val bodyMarkdown: String
    val commentsCount: Int
    val positiveReactionsCount: Int
    val publishedAt: Date
    val user: ArticleUser
}
