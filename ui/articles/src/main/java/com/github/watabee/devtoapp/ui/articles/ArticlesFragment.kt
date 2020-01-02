package com.github.watabee.devtoapp.ui.articles

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.github.watabee.devtoapp.pagenation.LoadMoreAdapter
import com.github.watabee.devtoapp.ui.article.ArticleFragment
import com.github.watabee.devtoapp.ui.articles.databinding.FragmentArticlesBinding
import com.google.android.material.snackbar.Snackbar
import me.saket.inboxrecyclerview.InboxRecyclerView
import me.saket.inboxrecyclerview.page.ExpandablePageLayout
import me.saket.inboxrecyclerview.page.PageStateChangeCallbacks
import javax.inject.Inject

internal class ArticlesFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory
) : Fragment(R.layout.fragment_articles) {

    private val viewModel: ArticlesViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = LoadMoreAdapter(viewModel::retry).apply {
            setHasStableIds(true)
            setOnItemClickListener { item, _ -> viewModel.selectArticle(item.id.toInt()) }
        }

        val binding = FragmentArticlesBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val expandablePage: ExpandablePageLayout = binding.expandablePageLayout
        val recyclerView: InboxRecyclerView = binding.recyclerView

        expandablePage.pushParentToolbarOnExpand(binding.toolbar)
        val onBackPressedCallback = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                if (expandablePage.isExpandedOrExpanding) {
                    recyclerView.collapse()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        expandablePage.addStateChangeCallbacks(object : PageStateChangeCallbacks {
            override fun onPageAboutToCollapse(collapseAnimDuration: Long) = Unit

            override fun onPageAboutToExpand(expandAnimDuration: Long) = Unit

            override fun onPageCollapsed() {
                onBackPressedCallback.isEnabled = false
            }

            override fun onPageExpanded() {
                onBackPressedCallback.isEnabled = true
            }
        })

        recyclerView.adapter = adapter
        recyclerView.expandablePage = expandablePage

        viewModel.openArticleDetail.observe(viewLifecycleOwner) { article ->
            childFragmentManager.commitNow(allowStateLoss = true) {
                replace(expandablePage.id, ArticleFragment.newInstance(article))
            }

            recyclerView.expandItem(article.id.toLong())
        }

        viewModel.articleUiModels.observe(viewLifecycleOwner) { articleUiModels ->
            adapter.update(articleUiModels.map(::ArticleBindableItem))
        }

        viewModel.loadMoreStatus.observe(viewLifecycleOwner) { loadMoreStatus ->
            adapter.status = loadMoreStatus
        }

        var snackbar: Snackbar? = null
        viewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                val currentSnackbar = snackbar
                if (currentSnackbar == null || !currentSnackbar.isShownOrQueued) {
                    snackbar = Snackbar.make(view, R.string.connection_error_message, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.retry) { viewModel.retry() }
                    snackbar?.show()
                }
            } else {
                snackbar?.dismiss()
                snackbar = null
            }
        }

        viewModel.refresh()
    }
}
