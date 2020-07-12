package com.github.watabee.devapp.pagenation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.github.watabee.devapp.ui.pagenation.R

class LoadStateViewHolder(parent: ViewGroup, private val retry: () -> Unit) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.list_item_load_state, parent, false)
) {
    private val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)
    private val retryText = itemView.findViewById<TextView>(R.id.retry_text).apply {
        setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState) {
        progressBar.isVisible = loadState is LoadState.Loading
        retryText.isVisible = loadState is LoadState.Error
    }
}
