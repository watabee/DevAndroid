package com.github.watabee.devtoapp.di

import android.content.Context
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.github.watabee.devtoapp.util.AppFragmentFactory
import com.github.watabee.devtoapp.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.noties.markwon.Markwon
import javax.inject.Singleton

@Module(includes = [UiCommonModule.Provider::class])
abstract class UiCommonModule {

    @Binds
    @Singleton
    internal abstract fun bindFragmentFactory(instance: AppFragmentFactory): FragmentFactory

    @Binds
    @Singleton
    internal abstract fun bindViewModelFactory(instance: ViewModelFactory): ViewModelProvider.Factory

    @Module
    object Provider {
        @Provides
        @Singleton
        internal fun provideMarkwon(context: Context): Markwon = Markwon.create(context)
    }
}
