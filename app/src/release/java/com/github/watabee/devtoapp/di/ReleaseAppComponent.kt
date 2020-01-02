package com.github.watabee.devtoapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ReleaseAppComponent : AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): ReleaseAppComponent
    }
}

fun createAppComponent(appContext: Context): AppComponent = DaggerReleaseAppComponent.factory().create(appContext)
