package com.github.watabee.rakutenapp

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity {

    constructor() : super()

    constructor(@LayoutRes layoutResId: Int) : super(layoutResId)

    @Inject lateinit var fragmentFactory: FragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
    }

    protected inline fun <reified T : Fragment> instantiateFragment(): Fragment =
        fragmentFactory.instantiate(classLoader, T::class.java.name)
}