package com.github.watabee.devtoapp.di

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.github.watabee.devtoapp.util.AppFragmentFactory
import com.github.watabee.devtoapp.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class UiCommonModule {

    @Binds
    @Singleton
    internal abstract fun bindFragmentFactory(instance: AppFragmentFactory): FragmentFactory

    @Binds
    @Singleton
    internal abstract fun bindViewModelFactory(instance: ViewModelFactory): ViewModelProvider.Factory
}
