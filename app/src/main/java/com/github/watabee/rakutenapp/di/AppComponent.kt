package com.github.watabee.rakutenapp.di

import com.github.watabee.rakutenapp.RakutenApp
import com.github.watabee.rakutenapp.appinitializers.AppInitializer
import dagger.android.AndroidInjector

interface AppComponent : AndroidInjector<RakutenApp>, ImageComponentFactoryProvider {

    fun appInitializers(): Set<@JvmSuppressWildcards AppInitializer>
}
