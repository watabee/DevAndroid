package com.github.watabee.devtoapp.ui.article

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.github.watabee.devtoapp.ui.articles.ArticlesViewModel
import com.github.watabee.devtoapp.ui.articles.R
import javax.inject.Inject

internal class ArticleFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory
) : Fragment(R.layout.fragment_article) {

    private val viewModel: ArticlesViewModel by viewModels({ requireParentFragment() }) { viewModelFactory }
}

private const val TAG = "ArticleFragment"

internal fun FragmentManager.findArticleFragment(): ArticleFragment? = findFragmentByTag(TAG) as? ArticleFragment
