package com.github.watabee.devtoapp.di

import com.github.watabee.devtoapp.DevToApp
import com.github.watabee.devtoapp.appinitializers.AppInitializer
import dagger.android.AndroidInjector

interface AppComponent : AndroidInjector<DevToApp>, ImageComponentFactoryProvider {

    fun appInitializers(): Set<@JvmSuppressWildcards AppInitializer>
}
