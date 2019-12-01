package com.github.watabee.rakutenapp.ui.ichiba.ranking

import com.github.watabee.rakutenapp.ui.ichiba.ranking.databinding.ListItemRankingBinding
import com.xwray.groupie.Item
import com.xwray.groupie.databinding.BindableItem

internal class RankingBindableItem(
    private val uiModel: RankingUiModel,
    private val onFavoriteButtonClicked: (uiModel: RankingUiModel) -> Unit
) : BindableItem<ListItemRankingBinding>(uiModel.itemCode.hashCode().toLong()) {

    override fun getLayout(): Int = R.layout.list_item_ranking

    override fun bind(viewBinding: ListItemRankingBinding, position: Int) {
        viewBinding.imageUrl = uiModel.imageUrl
        viewBinding.itemName = uiModel.itemName
        viewBinding.isFavorite = uiModel.isFavorite
        viewBinding.favoriteButton.setOnClickListener { onFavoriteButtonClicked(uiModel) }
    }

    override fun bind(viewBinding: ListItemRankingBinding, position: Int, payloads: MutableList<Any>) {
        val wasChangedFavoriteState = payloads.firstOrNull() as? Boolean ?: false
        if (wasChangedFavoriteState) {
            viewBinding.isFavorite = uiModel.isFavorite
        } else {
            bind(viewBinding, position)
        }
    }

    override fun hasSameContentAs(other: Item<*>): Boolean {
        if (other !is RankingBindableItem) {
            return false
        }
        return uiModel == other.uiModel
    }

    override fun getChangePayload(newItem: Item<*>): Any? {
        if (newItem !is RankingBindableItem) {
            return null
        }
        return newItem.uiModel.isFavorite != uiModel.isFavorite
    }
}
