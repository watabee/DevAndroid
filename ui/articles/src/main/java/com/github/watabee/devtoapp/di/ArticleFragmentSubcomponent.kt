package com.github.watabee.devtoapp.di

import com.github.watabee.devtoapp.ui.article.ArticleFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@FragmentScope
@Subcomponent
internal interface ArticleFragmentSubcomponent : AndroidInjector<ArticleFragment> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<ArticleFragment>
}
