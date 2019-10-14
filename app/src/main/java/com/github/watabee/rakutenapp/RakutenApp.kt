package com.github.watabee.rakutenapp

import com.github.watabee.rakutenapp.di.AppComponent
import com.github.watabee.rakutenapp.util.Logger
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class RakutenApp : DaggerApplication() {

    @Inject lateinit var logger: Logger

    override fun onCreate() {
        super.onCreate()

        logger.w("RakutenApp: onCreate()")
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        AppComponent(applicationContext)
}