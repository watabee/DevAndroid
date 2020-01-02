package com.github.watabee.devtoapp.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.github.watabee.devtoapp.ui.article.ArticleFragment
import com.github.watabee.devtoapp.ui.articles.ArticlesFragment
import com.github.watabee.devtoapp.ui.articles.ArticlesViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@AssistedModule
@Module(
    includes = [AssistedInject_UiArticlesModule::class],
    subcomponents = [ArticleFragmentSubcomponent::class]
)
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
    @ClassKey(ArticleFragment::class)
    internal abstract fun bindArticleFragmentInjectorFactory(instance: ArticleFragmentSubcomponent.Factory): AndroidInjector.Factory<*>
}
