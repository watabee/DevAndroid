package com.github.watabee.rakutenapp.pagenation

import com.github.watabee.rakutenapp.ui.pagenation.R
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section

class LoadMoreAdapter(private val retry: (() -> Unit)? = null) : GroupAdapter<GroupieViewHolder>() {
    private val viewSection = Section()
    private val extraSection = Section()

    private val loadingItem = listOf(LoadingItem())
    private val errorItem = listOf(ErrorItem())

    var status: LoadMoreStatus = LoadMoreStatus.IDLE
        set(value) {
            field = value
            when (value) {
                LoadMoreStatus.IDLE -> extraSection.update(emptyList())
                LoadMoreStatus.LOADING -> extraSection.update(loadingItem)
                LoadMoreStatus.ERROR -> extraSection.update(errorItem)
            }
        }

    init {
        addAll(listOf(viewSection, extraSection))
        setOnItemClickListener(null)
    }

    override fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        val newOnItemClickListener = OnItemClickListener { item, view ->
            when (item) {
                is ErrorItem -> {
                    retry?.invoke()
                }
                is LoadingItem -> {
                    // do nothing
                }
                else -> {
                    onItemClickListener?.onItemClick(item, view)
                }
            }
        }

        super.setOnItemClickListener(newOnItemClickListener)
    }

    override fun update(newGroups: Collection<Group>) {
        viewSection.update(newGroups)
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
