package com.github.watabee.rakutenapp.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.github.watabee.rakutenapp.ui.ichiba.ranking.RankingFragment
import com.github.watabee.rakutenapp.ui.ichiba.ranking.RankingViewModel
import com.github.watabee.rakutenapp.util.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [UiRankingAssistedModule::class])
abstract class UiRankingModule {

    @Binds
    @IntoMap
    @FragmentKey(RankingFragment::class)
    internal abstract fun bindFragment(fragment: RankingFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(RankingViewModel::class)
    internal abstract fun bindViewModelFactory(factory: RankingViewModel.Factory): AppViewModelFactory<out ViewModel>
}
