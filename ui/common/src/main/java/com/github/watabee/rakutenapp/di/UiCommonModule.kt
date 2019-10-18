package com.github.watabee.rakutenapp.di

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.github.watabee.rakutenapp.util.AppFragmentFactory
import com.github.watabee.rakutenapp.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [UiCommonAssistedModule::class])
abstract class UiCommonModule {

    @Binds
    @Singleton
    internal abstract fun bindFragmentFactory(instance: AppFragmentFactory): FragmentFactory

    @Binds
    @Singleton
    internal abstract fun bindViewModelFactory(
        instance: ViewModelFactory
    ): ViewModelProvider.Factory
}