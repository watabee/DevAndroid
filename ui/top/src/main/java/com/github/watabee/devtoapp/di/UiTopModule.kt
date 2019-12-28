package com.github.watabee.devtoapp.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.github.watabee.devtoapp.ui.top.TopActivity
import com.github.watabee.devtoapp.ui.top.TopFragment
import com.github.watabee.devtoapp.ui.top.TopViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class UiTopModule {

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun contributeActivityInjector(): TopActivity

    @Binds
    @IntoMap
    @FragmentKey(TopFragment::class)
    internal abstract fun bindFragment(fragment: TopFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(TopViewModel::class)
    internal abstract fun bindTopViewModel(instance: TopViewModel): ViewModel
}
