package com.github.watabee.devtoapp.ui.articles

import com.github.watabee.devtoapp.ui.articles.databinding.ListItemArticleBinding
import com.xwray.groupie.Item
import com.xwray.groupie.databinding.BindableItem

internal class ArticleBindableItem(private val uiModel: ArticleUiModel) : BindableItem<ListItemArticleBinding>(uiModel.id.toLong()) {
    override fun getLayout(): Int = R.layout.list_item_article

    override fun bind(viewBinding: ListItemArticleBinding, position: Int) {
        viewBinding.uiModel = uiModel
    }

    override fun hasSameContentAs(other: Item<*>): Boolean = other is ArticleBindableItem && uiModel == other.uiModel
}
