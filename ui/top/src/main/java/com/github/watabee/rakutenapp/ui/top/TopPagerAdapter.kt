package com.github.watabee.rakutenapp.ui.top

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

internal class TopPagerAdapter(
    fragmentManager: FragmentManager,
    private val classLoader: ClassLoader
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentFactory: FragmentFactory = fragmentManager.fragmentFactory

    override fun getItem(position: Int): Fragment {
        val topPageItem: TopPageItem = TopPageItem.values()[position]
        return fragmentFactory.instantiate(classLoader, topPageItem.className)
    }

    override fun getCount(): Int = TopPageItem.values().size
}