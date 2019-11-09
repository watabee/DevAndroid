package com.github.watabee.rakutenapp

import com.github.watabee.rakutenapp.di.AppComponent
import com.github.watabee.rakutenapp.di.ImageComponent
import com.github.watabee.rakutenapp.di.ImageComponentFactoryProvider
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class RakutenApp : DaggerApplication(), ImageComponentFactoryProvider {

    private val appComponent by lazy { AppComponent(applicationContext) }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent

    override fun glideComponentFactory(): ImageComponent.Factory = appComponent.glideComponentFactory()
}
