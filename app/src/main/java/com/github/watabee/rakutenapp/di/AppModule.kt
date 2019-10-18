package com.github.watabee.rakutenapp.di

import dagger.Module

@Module(
    includes = [
        BaseModule::class,
        UiCommonModule::class,
        UiTopModule::class
    ]
)
object AppModule