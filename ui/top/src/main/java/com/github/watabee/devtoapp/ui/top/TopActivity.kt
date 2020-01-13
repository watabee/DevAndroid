package com.github.watabee.devtoapp.ui.top

import android.os.Bundle
import android.view.View
import com.github.watabee.devtoapp.BaseActivity
import com.github.watabee.devtoapp.ui.signin.SignInFragment
import com.github.watabee.devtoapp.ui.top.databinding.ActivityTopBinding

class TopActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        SignInFragment.injectIfNeededIn(supportFragmentManager)
    }
}
