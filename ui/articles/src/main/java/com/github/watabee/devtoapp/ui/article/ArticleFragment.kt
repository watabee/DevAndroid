package com.github.watabee.devtoapp.ui.article

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.github.watabee.devtoapp.ui.articles.ArticlesViewModel
import com.github.watabee.devtoapp.ui.articles.R
import com.github.watabee.devtoapp.ui.articles.databinding.FragmentArticleBinding
import io.noties.markwon.Markwon
import me.saket.inboxrecyclerview.globalVisibleRect
import me.saket.inboxrecyclerview.page.ExpandablePageLayout
import me.saket.inboxrecyclerview.page.InterceptResult
import javax.inject.Inject

internal class ArticleFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val markwon: Markwon
) : Fragment(R.layout.fragment_article) {

    private val viewModel: ArticlesViewModel by viewModels({ requireParentFragment() }) { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArticleBinding.bind(view)
        binding.markwon = markwon

        val scrollView = binding.scrollView

        var articleId = 0
        viewModel.selectedArticle.observe(viewLifecycleOwner) { article ->
            if (articleId != article.id) {
                articleId = article.id
                scrollView.scrollTo(0, 0)
            }
            binding.uiModel = article
            binding.executePendingBindings()
        }

        val expandablePage = view.parent as ExpandablePageLayout

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
}

private const val TAG = "ArticleFragment"

internal fun FragmentManager.findArticleFragment(): ArticleFragment? = findFragmentByTag(TAG) as? ArticleFragment
