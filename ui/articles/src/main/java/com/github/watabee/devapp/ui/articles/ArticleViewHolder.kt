package com.github.watabee.devapp.ui.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.github.watabee.devapp.ui.articles.databinding.ListItemArticleBinding
import com.google.android.material.chip.Chip

internal class ArticleViewHolder(
    parent: ViewGroup,
    private val onTagClicked: (String) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.list_item_article, parent, false)
) {
    private val binding: ListItemArticleBinding = ListItemArticleBinding.bind(itemView)

    fun bind(uiModel: ArticleUiModel) {
        binding.uiModel = uiModel
        binding.chipGroup.children.forEach {
            val chip = it as? Chip
            chip?.setOnClickListener {
                onTagClicked(chip.text.toString())
            }
        }

        binding.executePendingBindings()
    }
}
