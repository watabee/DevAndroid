package com.github.watabee.rakutenapp

import com.github.watabee.rakutenapp.di.AppComponent
import com.github.watabee.rakutenapp.di.ImageComponent
import com.github.watabee.rakutenapp.di.ImageComponentFactoryProvider
import com.github.watabee.rakutenapp.di.createAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import kotlin.LazyThreadSafetyMode.NONE

class RakutenApp : DaggerApplication(), ImageComponentFactoryProvider {

    private val appComponent: AppComponent by lazy(NONE) { createAppComponent(this) }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent

    override fun glideComponentFactory(): ImageComponent.Factory = appComponent.glideComponentFactory()

    override fun onCreate() {
        super.onCreate()

        appComponent.appInitializers().forEach { it.init(this) }
    }
}
