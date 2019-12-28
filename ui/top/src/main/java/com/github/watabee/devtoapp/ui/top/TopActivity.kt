package com.github.watabee.devtoapp.ui.top

import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.github.watabee.devtoapp.BaseActivity
import com.github.watabee.devtoapp.ui.signin.SignInFragment
import com.github.watabee.devtoapp.ui.top.databinding.ActivityTopBinding

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
                    viewPager.setCurrentItem(TopPageItem.ICHIBA.ordinal, true)
                }
                R.id.bottom_nav_books -> {
                    viewPager.setCurrentItem(TopPageItem.BOOKS.ordinal, true)
                }
                R.id.bottom_nav_foo -> {
                    viewPager.setCurrentItem(TopPageItem.FOO.ordinal, true)
                }
                R.id.bottom_nav_bar -> {
                    viewPager.setCurrentItem(TopPageItem.BAR.ordinal, true)
                }
            }
            true
        }

        SignInFragment.injectIfNeededIn(supportFragmentManager)
    }
}
