package com.github.watabee.rakutenapp.ui.ichiba.ranking

import com.github.watabee.rakutenapp.ui.ichiba.ranking.databinding.ListItemRankingBinding
import com.xwray.groupie.Item
import com.xwray.groupie.databinding.BindableItem

internal class RankingBindableItem(
    private val uiModel: RankingUiModel
) : BindableItem<ListItemRankingBinding>(uiModel.itemCode.hashCode().toLong()) {

    override fun getLayout(): Int = R.layout.list_item_ranking

    override fun bind(viewBinding: ListItemRankingBinding, position: Int) {
        viewBinding.uiModel = uiModel
    }

    override fun hasSameContentAs(other: Item<*>): Boolean {
        if (other !is RankingBindableItem) {
            return false
        }
        return uiModel == other.uiModel
    }
}
