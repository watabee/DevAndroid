package com.github.watabee.rakutenapp.ui

import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.ListenerUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.watabee.rakutenapp.pagenation.LoadMoreAdapter
import com.github.watabee.rakutenapp.pagenation.OnLoadMore
import com.github.watabee.rakutenapp.ui.pagenation.R

@BindingAdapter("onLoadMore")
fun setOnLoadMore(view: RecyclerView, onLoadMore: OnLoadMore) {

    val newListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val layoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return
            val adapter = recyclerView.adapter as? LoadMoreAdapter ?: return

            val totalItemCount = layoutManager.itemCount
            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
            if (totalItemCount > 0 && totalItemCount == lastVisibleItem + 1) {
                if (totalItemCount == 1 && adapter.getItemViewType(0) == R.layout.list_item_error) {
                    return
                }
                view.post { onLoadMore() }
            }
        }
    }

    val oldListener = ListenerUtil.trackListener(view, newListener, R.id.load_more_listener)
    if (oldListener != null) {
        view.removeOnScrollListener(oldListener)
    }
    view.addOnScrollListener(newListener)
}
