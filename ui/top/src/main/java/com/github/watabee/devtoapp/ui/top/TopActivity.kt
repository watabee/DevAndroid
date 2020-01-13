package com.github.watabee.devtoapp.ui.top

import android.os.Bundle
import com.github.watabee.devtoapp.BaseActivity
import com.github.watabee.devtoapp.ui.signin.SignInFragment

class TopActivity : BaseActivity(R.layout.activity_top) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SignInFragment.injectIfNeededIn(supportFragmentManager)
    }
}
