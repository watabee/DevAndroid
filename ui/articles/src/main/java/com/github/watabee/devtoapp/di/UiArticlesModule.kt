package com.github.watabee.devtoapp.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.github.watabee.devtoapp.ui.article.ArticleFragment
import com.github.watabee.devtoapp.ui.articles.ArticlesFragment
import com.github.watabee.devtoapp.ui.articles.ArticlesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UiArticlesModule {

    @Binds
    @IntoMap
    @FragmentKey(ArticlesFragment::class)
    internal abstract fun bindArticlesFragment(instance: ArticlesFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(ArticlesViewModel::class)
    internal abstract fun bindArticlesViewModel(instance: ArticlesViewModel): ViewModel

    @Binds
    @IntoMap
    @FragmentKey(ArticleFragment::class)
    internal abstract fun bindArticleFragment(instance: ArticleFragment): Fragment
}
