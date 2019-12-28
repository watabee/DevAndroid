package com.github.watabee.rakutenapp.di

import com.github.watabee.rakutenapp.DevToApp
import com.github.watabee.rakutenapp.appinitializers.AppInitializer
import dagger.android.AndroidInjector

interface AppComponent : AndroidInjector<DevToApp>, ImageComponentFactoryProvider {

    fun appInitializers(): Set<@JvmSuppressWildcards AppInitializer>
}
