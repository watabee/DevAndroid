package com.github.watabee.rakutenapp.di

import androidx.fragment.app.FragmentFactory
import com.github.watabee.rakutenapp.util.AppFragmentFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class UiCommonModule {

    @Binds
    @Singleton
    internal abstract fun bindFragmentFactory(instance: AppFragmentFactory): FragmentFactory
}