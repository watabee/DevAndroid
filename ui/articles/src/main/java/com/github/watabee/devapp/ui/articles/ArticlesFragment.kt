package com.github.watabee.devapp.ui.articles

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.watabee.devapp.pagenation.LoadStateAdapter
import com.github.watabee.devapp.ui.article.ArticleFragment
import com.github.watabee.devapp.ui.articles.databinding.FragmentArticlesBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.saket.inboxrecyclerview.InboxRecyclerView
import me.saket.inboxrecyclerview.page.ExpandablePageLayout
import me.saket.inboxrecyclerview.page.PageStateChangeCallbacks

@AndroidEntryPoint
internal class ArticlesFragment : Fragment(R.layout.fragment_articles) {

    private val viewModel: ArticlesViewModel by viewModels()

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArticlesBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val expandablePage: ExpandablePageLayout = binding.expandablePageLayout
        val recyclerView: InboxRecyclerView = binding.recyclerView
        val swipeRefreshLayout: SwipeRefreshLayout = binding.swipeRefreshLayout

        val articlesAdapter = ArticlesAdapter(
            onItemClicked = { article ->
                childFragmentManager.commitNow(allowStateLoss = true) {
                    replace(expandablePage.id, ArticleFragment.newInstance(article))
                }

                recyclerView.expandItem(article.id.toLong())
            },
            onTagClicked = viewModel::filterByTag
        ).apply {
            setHasStableIds(true)
        }

        val loadStateAdapter = LoadStateAdapter(articlesAdapter::retry)
            .apply { setHasStableIds(true) }
        articlesAdapter.addLoadStateListener { loadStateAdapter.loadState = it.append }
        val adapter = ConcatAdapter(
            ConcatAdapter.Config.Builder().setStableIdMode(ConcatAdapter.Config.StableIdMode.ISOLATED_STABLE_IDS).build(),
            articlesAdapter,
            loadStateAdapter
        )

        var searchJob: Job? = null
        viewModel.searchResult.observe(viewLifecycleOwner) { searchResult ->
            searchJob?.cancel()
            searchJob = viewLifecycleOwner.lifecycleScope.launch {
                searchResult.collectLatest(articlesAdapter::submitData)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            articlesAdapter.dataRefreshFlow
                .collectLatest { isEmpty ->
                    if (!isEmpty) {
                        recyclerView.scrollToPosition(0)
                    }
                }
        }

        var snackbar: Snackbar? = null
        viewLifecycleOwner.lifecycleScope.launch {
            articlesAdapter.loadStateFlow
                .collectLatest { loadStates ->
                    swipeRefreshLayout.isRefreshing = loadStates.refresh == LoadState.Loading

                    if (loadStates.refresh is LoadState.Error) {
                        val currentSnackbar = snackbar
                        if (currentSnackbar == null || !currentSnackbar.isShownOrQueued) {
                            snackbar = Snackbar.make(view, R.string.connection_error_message, Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.retry) { articlesAdapter.retry() }
                            snackbar?.show()
                        }
                    } else {
                        snackbar?.dismiss()
                        snackbar = null
                    }
                }
        }

        swipeRefreshLayout.setOnRefreshListener { articlesAdapter.refresh() }

        expandablePage.pushParentToolbarOnExpand(binding.appBarLayout)
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

            override fun onPageAboutToExpand(expandAnimDuration: Long) {
                viewModel.showFilterButton(false)
            }

            override fun onPageCollapsed() {
                onBackPressedCallback.isEnabled = false
                viewModel.showFilterButton(true)

                val decorView = requireActivity().window.decorView
                val options = decorView.systemUiVisibility and
                    View.SYSTEM_UI_FLAG_FULLSCREEN.inv() and
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION.inv() and
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY.inv()

                decorView.systemUiVisibility = options
            }

            override fun onPageExpanded() {
                onBackPressedCallback.isEnabled = true
            }
        })

        recyclerView.adapter = adapter
        recyclerView.expandablePage = expandablePage
    }
}
