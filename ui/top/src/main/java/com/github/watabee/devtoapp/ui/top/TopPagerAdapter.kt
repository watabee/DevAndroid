package com.github.watabee.devtoapp.ui.top

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.viewpager2.adapter.FragmentStateAdapter

internal class TopPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragmentFactory: FragmentFactory = activity.supportFragmentManager.fragmentFactory
    private val classLoader: ClassLoader = activity.classLoader

    override fun createFragment(position: Int): Fragment {
        val topPageItem: TopPageItem = TopPageItem.values()[position]
        return fragmentFactory.instantiate(classLoader, topPageItem.className)
    }

    override fun getItemCount(): Int = TopPageItem.values().size
}
