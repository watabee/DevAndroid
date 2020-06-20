package com.github.watabee.devtoapp.di

import com.github.watabee.devtoapp.util.AppLogger
import com.github.watabee.devtoapp.util.Logger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class AppModule {
    @Singleton
    @Binds
    internal abstract fun bindLogger(instance: AppLogger): Logger
}
