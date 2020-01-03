package com.github.watabee.devtoapp.ui.articles

import androidx.core.view.children
import com.github.watabee.devtoapp.ui.articles.databinding.ListItemArticleBinding
import com.google.android.material.chip.Chip
import com.xwray.groupie.Item
import com.xwray.groupie.databinding.BindableItem

internal class ArticleBindableItem(
    private val uiModel: ArticleUiModel,
    private val onTagClicked: (String) -> Unit
) : BindableItem<ListItemArticleBinding>(uiModel.id.toLong()) {
    override fun getLayout(): Int = R.layout.list_item_article

    override fun bind(viewBinding: ListItemArticleBinding, position: Int) {
        viewBinding.uiModel = uiModel
        viewBinding.chipGroup.children.forEach {
            val chip = it as? Chip
            chip?.setOnClickListener {
                onTagClicked(chip.text.toString())
            }
        }
    }

    override fun hasSameContentAs(other: Item<*>): Boolean = other is ArticleBindableItem && uiModel == other.uiModel
}
