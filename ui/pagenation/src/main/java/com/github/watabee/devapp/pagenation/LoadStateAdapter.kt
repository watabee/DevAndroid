package com.github.watabee.devapp.pagenation

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class LoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadStateViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(parent, retry)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}
