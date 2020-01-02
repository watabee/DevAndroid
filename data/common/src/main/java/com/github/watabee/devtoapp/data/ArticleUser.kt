package com.github.watabee.devtoapp.data

import java.io.Serializable

interface ArticleUser : Serializable {
    val name: String
    val username: String
    val profileImage: String
    val profileImage90: String
}
