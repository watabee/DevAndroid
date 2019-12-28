package com.github.watabee.devtoapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_ichiba_items"
)
data class FavoriteIchibaItem(
    @PrimaryKey val itemCode: String,
    val itemName: String,
    val imageUrl: String?,
    val itemPrice: String
)
