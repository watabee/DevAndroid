package com.github.watabee.rakutenapp.ui.top

import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.github.watabee.rakutenapp.BaseActivity
import com.github.watabee.rakutenapp.ui.top.databinding.ActivityTopBinding

class TopActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTopBinding = ActivityTopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = TopPagerAdapter(this)
        viewPager.isUserInputEnabled = false

        binding.bottomNavView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.bottom_nav_ichiba -> {
                    viewPager.setCurrentItem(0, true)
                }
                R.id.bottom_nav_books -> {
                    viewPager.setCurrentItem(1, true)
                }
                R.id.bottom_nav_foo -> {
                    viewPager.setCurrentItem(2, true)
                }
                R.id.bottom_nav_bar -> {
                    viewPager.setCurrentItem(3, true)
                }
            }
            true
        }
    }
}