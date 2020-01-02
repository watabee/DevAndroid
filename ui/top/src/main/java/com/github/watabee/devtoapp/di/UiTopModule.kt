package com.github.watabee.devtoapp.di

import com.github.watabee.devtoapp.ui.top.TopActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiTopModule {

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun contributeActivityInjector(): TopActivity
}
