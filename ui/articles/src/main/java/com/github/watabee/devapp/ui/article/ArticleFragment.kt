package com.github.watabee.devapp.ui.article

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commitNow
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.github.watabee.devapp.data.Article
import com.github.watabee.devapp.ui.articles.R
import com.github.watabee.devapp.ui.articles.databinding.FragmentArticleBinding
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

@AndroidEntryPoint
internal class ArticleFragment : Fragment(R.layout.fragment_article) {

    @Inject lateinit var viewModelFactory: ArticleViewModel.Factory
    @Inject lateinit var markwon: Markwon

    private val article: Article by lazy(NONE) {
        requireArguments().getSerializable(ARTICLE) as Article
    }

    @Suppress("UNCHECKED_CAST")
    private val viewModel: ArticleViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return viewModelFactory.create(article) as T
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArticleBinding.bind(view)
        binding.markwon = markwon

        viewModel.articleDetailUiModel.observe(viewLifecycleOwner) { uiModel ->
            binding.uiModel = uiModel
            binding.executePendingBindings()
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.commitNow { remove(this@ArticleFragment) }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    companion object {
        private const val ARTICLE = "article"

        fun newInstance(article: Article): ArticleFragment =
            ArticleFragment().apply { arguments = bundleOf(ARTICLE to article) }
    }
}

private const val TAG = "ArticleFragment"

fun FragmentManager.addArticleFragment(containerViewId: Int, article: Article) {
    commitNow {
        add(containerViewId, ArticleFragment.newInstance(article), TAG)
    }
}
