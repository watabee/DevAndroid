package com.github.watabee.devapp.ui.top

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.watabee.devapp.ui.signin.SignInFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopActivity : AppCompatActivity(R.layout.activity_top) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SignInFragment.injectIfNeededIn(supportFragmentManager)
    }
}
