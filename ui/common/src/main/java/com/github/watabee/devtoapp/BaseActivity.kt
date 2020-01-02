package com.github.watabee.devtoapp

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity, HasAndroidInjector {

    constructor() : super()

    constructor(@LayoutRes layoutResId: Int) : super(layoutResId)

    @Inject lateinit var fragmentFactory: FragmentFactory
    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    protected inline fun <reified T : Fragment> instantiateFragment(): Fragment =
        fragmentFactory.instantiate(classLoader, T::class.java.name)
}
