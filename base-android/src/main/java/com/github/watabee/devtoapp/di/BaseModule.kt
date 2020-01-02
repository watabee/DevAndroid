package com.github.watabee.devtoapp.di

import com.github.watabee.devtoapp.util.AppLogger
import com.github.watabee.devtoapp.util.CoroutineDispatchers
import com.github.watabee.devtoapp.util.Logger
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers

@Module(includes = [BaseModule.Provider::class])
abstract class BaseModule {

    @Singleton
    @Binds
    internal abstract fun bindLogger(instance: AppLogger): Logger

    @Module
    internal object Provider {
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
