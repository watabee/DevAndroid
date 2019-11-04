package com.github.watabee.rakutenapp.di

import dagger.Module

@Module(
    includes = [
        BaseModule::class,
        ApiModule::class,
        UiCommonModule::class,
        UiTopModule::class,
        UiRankingModule::class
    ]
)
object AppModule
