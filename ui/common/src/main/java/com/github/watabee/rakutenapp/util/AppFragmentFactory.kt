package com.github.watabee.rakutenapp.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject
import javax.inject.Provider

internal class AppFragmentFactory @Inject constructor(
    private val providers: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragmentClass: Class<out Fragment> = loadFragmentClass(classLoader, className)
        return providers[fragmentClass]?.get() ?: super.instantiate(classLoader, className)
    }
}
