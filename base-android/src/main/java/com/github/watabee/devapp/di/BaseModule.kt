package com.github.watabee.devapp.di

import com.github.watabee.devapp.util.CoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object BaseModule {

    @Provides
    @Singleton
    fun provideCoroutineDispatchers(): CoroutineDispatchers =
        CoroutineDispatchers(
            main = Dispatchers.Main,
            io = Dispatchers.IO,
            computation = Dispatchers.Default
        )
}
