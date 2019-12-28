package com.github.watabee.devtoapp.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.github.watabee.devtoapp.ui.ichiba.ranking.RankingFragment
import com.github.watabee.devtoapp.ui.ichiba.ranking.RankingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UiRankingModule {

    @Binds
    @IntoMap
    @FragmentKey(RankingFragment::class)
    internal abstract fun bindFragment(fragment: RankingFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(RankingViewModel::class)
    internal abstract fun bindViewModel(factory: RankingViewModel): ViewModel
}
