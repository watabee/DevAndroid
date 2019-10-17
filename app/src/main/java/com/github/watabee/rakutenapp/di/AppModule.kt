package com.github.watabee.rakutenapp.di

import dagger.Module

@Module(
    includes = [
        BaseModule::class,
        ActivityBindingModule::class,
        UiCommonModule::class
    ]
)
object AppModule