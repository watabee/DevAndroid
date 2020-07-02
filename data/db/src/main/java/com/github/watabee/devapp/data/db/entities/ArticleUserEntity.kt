package com.github.watabee.devapp.data.db.entities

import androidx.room.ColumnInfo
import com.github.watabee.devapp.data.ArticleUser

data class ArticleUserEntity(
    override val name: String,
    override val username: String,
    @ColumnInfo(name = "profile_image") override val profileImage: String,
    @ColumnInfo(name = "profile_image_90") override val profileImage90: String
) : ArticleUser {

    constructor(user: ArticleUser) : this(
        name = user.name, username = user.username, profileImage = user.profileImage, profileImage90 = user.profileImage90
    )

    companion object {
        const val serialVersionUID = 1L
    }
}
