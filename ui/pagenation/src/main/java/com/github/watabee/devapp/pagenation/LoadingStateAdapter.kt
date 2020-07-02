package com.github.watabee.devapp.pagenation

import com.github.watabee.devapp.ui.pagenation.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.OnItemClickListener

class LoadingStateAdapter(private val retry: (() -> Unit)? = null) : GroupAdapter<GroupieViewHolder>() {
    private val loadingItem = listOf(LoadingItem())
    private val errorItem = listOf(ErrorItem())

    var status: LoadMoreStatus = LoadMoreStatus.IDLE
        set(value) {
            field = value
            when (value) {
                LoadMoreStatus.IDLE -> update(emptyList())
                LoadMoreStatus.LOADING -> update(loadingItem)
                LoadMoreStatus.ERROR -> update(errorItem)
            }
        }

    init {
        setOnItemClickListener(null)
    }

    override fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        val newOnItemClickListener = OnItemClickListener { item, _ ->
            when (item) {
                is ErrorItem -> {
                    retry?.invoke()
                }
                is LoadingItem -> {
                    // do nothing
                }
                else -> {
                    throw IllegalStateException("Unknown item type")
                }
            }
        }

        super.setOnItemClickListener(newOnItemClickListener)
    }
}

private class LoadingItem : Item<GroupieViewHolder>(0L) {
    override fun getLayout(): Int = R.layout.list_item_loading
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        // do nothing
    }

    override fun hasSameContentAs(other: Item<*>): Boolean = other is LoadingItem
    override fun isClickable(): Boolean = false
}

private class ErrorItem : Item<GroupieViewHolder>(0L) {
    override fun getLayout(): Int = R.layout.list_item_error
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        // do nothing
    }

    override fun hasSameContentAs(other: Item<*>): Boolean = other is ErrorItem
    override fun isClickable(): Boolean = true
}
