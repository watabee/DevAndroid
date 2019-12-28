package com.github.watabee.devtoapp.ui.ichiba.ranking

internal data class RankingUiModel(
    val itemCode: String,
    val itemName: String,
    val imageUrl: String?,
    val itemPrice: String,
    val isFavorite: Boolean
)
