package com.github.watabee.devapp.ui.articles

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.github.watabee.devapp.data.Article

internal class ArticlesAdapter(
    private val onItemClicked: (Article) -> Unit,
    private val onTagClicked: (String) -> Unit
) : PagingDataAdapter<ArticleUiModel, ArticleViewHolder>(createDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(parent, onTagClicked).apply {
            itemView.setOnClickListener {
                getItem(bindingAdapterPosition)?.let { onItemClicked(it.article) }
            }
        }
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val uiModel = getItem(position) ?: return
        holder.bind(uiModel)
    }

    companion object {
        private fun createDiffCallback() = object : DiffUtil.ItemCallback<ArticleUiModel>() {
            override fun areItemsTheSame(oldItem: ArticleUiModel, newItem: ArticleUiModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArticleUiModel, newItem: ArticleUiModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}


