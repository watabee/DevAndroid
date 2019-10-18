package com.github.watabee.rakutenapp.di

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module

@AssistedModule
@Module(includes = [AssistedInject_UiCommonAssistedModule::class])
internal object UiCommonAssistedModule
