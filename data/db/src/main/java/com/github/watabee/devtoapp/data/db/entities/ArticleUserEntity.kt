package com.github.watabee.devtoapp.data.db.entities

import androidx.room.ColumnInfo

data class ArticleUserEntity(
    val name: String,
    val username: String,
    @ColumnInfo(name = "profile_image") val profileImage: String,
    @ColumnInfo(name = "profile_image_90") val profileImage90: String
)
