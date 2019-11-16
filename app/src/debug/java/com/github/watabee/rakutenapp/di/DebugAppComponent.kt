package com.github.watabee.rakutenapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DebugAppModule::class])
interface DebugAppComponent : AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): DebugAppComponent
    }
}

fun createAppComponent(appContext: Context): AppComponent = DaggerDebugAppComponent.factory().create(appContext)
