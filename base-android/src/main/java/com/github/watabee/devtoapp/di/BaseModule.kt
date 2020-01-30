package com.github.watabee.devtoapp.di

import com.github.watabee.devtoapp.util.AppLogger
import com.github.watabee.devtoapp.util.CoroutineDispatchers
import com.github.watabee.devtoapp.util.Logger
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
abstract class BaseModule {

    @Singleton
    @Binds
    internal abstract fun bindLogger(instance: AppLogger): Logger

    companion object {
        @Provides
        @Singleton
        fun provideCoroutineDispatchers(): CoroutineDispatchers =
            CoroutineDispatchers(
                main = Dispatchers.Main,
                io = Dispatchers.IO,
                computation = Dispatchers.Default
            )
    }
}
