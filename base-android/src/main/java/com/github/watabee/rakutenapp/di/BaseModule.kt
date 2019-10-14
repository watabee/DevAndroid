package com.github.watabee.rakutenapp.di

import com.github.watabee.rakutenapp.util.AppLogger
import com.github.watabee.rakutenapp.util.Logger
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class BaseModule {

    @Singleton
    @Binds
    internal abstract fun bindLogger(instance: AppLogger): Logger
}