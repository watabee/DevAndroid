package com.github.watabee.devtoapp.di

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@AssistedModule
@Module(includes = [AssistedInject_UiArticleModule::class])
interface UiArticleModule
