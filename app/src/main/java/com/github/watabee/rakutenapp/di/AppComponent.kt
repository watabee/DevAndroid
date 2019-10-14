package com.github.watabee.rakutenapp.di

import android.content.Context
import com.github.watabee.rakutenapp.RakutenApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<RakutenApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): AppComponent
    }

    companion object {
        operator fun invoke(appContext: Context): AppComponent =
            DaggerAppComponent.factory().create(appContext)
    }
}