package com.github.watabee.devtoapp

import com.github.watabee.devtoapp.di.AppComponent
import com.github.watabee.devtoapp.di.ImageComponent
import com.github.watabee.devtoapp.di.ImageComponentFactoryProvider
import com.github.watabee.devtoapp.di.createAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import kotlin.LazyThreadSafetyMode.NONE

class DevToApp : DaggerApplication(), ImageComponentFactoryProvider {

    private val appComponent: AppComponent by lazy(NONE) { createAppComponent(this) }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent

    override fun glideComponentFactory(): ImageComponent.Factory = appComponent.glideComponentFactory()

    override fun onCreate() {
        super.onCreate()

        appComponent.appInitializers().forEach { it.init(this) }
    }
}
