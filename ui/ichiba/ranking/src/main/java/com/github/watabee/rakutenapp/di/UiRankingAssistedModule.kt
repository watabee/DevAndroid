package com.github.watabee.rakutenapp.di

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module

@AssistedModule
@Module(includes = [AssistedInject_UiRankingAssistedModule::class])
internal abstract class UiRankingAssistedModule
