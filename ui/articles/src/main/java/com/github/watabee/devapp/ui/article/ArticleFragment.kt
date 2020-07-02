package com.github.watabee.devapp.ui.article

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.github.watabee.devapp.data.Article
import com.github.watabee.devapp.ui.articles.R
import com.github.watabee.devapp.ui.articles.databinding.FragmentArticleBinding
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import me.saket.inboxrecyclerview.globalVisibleRect
import me.saket.inboxrecyclerview.page.ExpandablePageLayout
import me.saket.inboxrecyclerview.page.InterceptResult
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

        val decorView = requireActivity().window.decorView
        decorView.systemUiVisibility = decorView.systemUiVisibility or
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        val expandablePage = view.parent as ExpandablePageLayout
        val scrollView = binding.scrollView

        expandablePage.pullToCollapseInterceptor = { downX, downY, upwardPull ->
            if (scrollView.globalVisibleRect().contains(downX, downY).not()) {
                InterceptResult.IGNORED
            } else {
                val directionInt = if (upwardPull) +1 else -1
                val canScrollFurther = scrollView.canScrollVertically(directionInt)
                when {
                    canScrollFurther -> InterceptResult.INTERCEPTED
                    else -> InterceptResult.IGNORED
                }
            }
        }
    }

    companion object {
        private const val ARTICLE = "article"

        fun newInstance(article: Article): ArticleFragment =
            ArticleFragment().apply { arguments = bundleOf(ARTICLE to article) }
    }
}
