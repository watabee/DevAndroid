package com.github.watabee.rakutenapp

import com.github.watabee.rakutenapp.di.AppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class RakutenApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        AppComponent(applicationContext)
}
