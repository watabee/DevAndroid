package com.github.watabee.devapp.di

import com.github.watabee.devapp.util.AppLogger
import com.github.watabee.devapp.util.Logger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Singleton
    @Binds
    internal abstract fun bindLogger(instance: AppLogger): Logger
}
