package com.github.watabee.devapp.ui.articles

import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.watabee.devapp.pagenation.LoadStateAdapter
import com.github.watabee.devapp.ui.article.addArticleFragment
import com.github.watabee.devapp.ui.articles.databinding.FragmentArticlesBinding
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class ArticlesFragment : Fragment(R.layout.fragment_articles) {

    private val viewModel: ArticlesViewModel by viewModels()

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArticlesBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val recyclerView: RecyclerView = binding.recyclerView
        val swipeRefreshLayout: SwipeRefreshLayout = binding.swipeRefreshLayout
        val fragmentContainerView = binding.fragmentContainerView

        val articlesAdapter = ArticlesAdapter(
            onItemClicked = { article ->
                childFragmentManager.addArticleFragment(fragmentContainerView.id, article)
            },
            onTagClicked = viewModel::filterByTag
        )

        val loadStateAdapter = LoadStateAdapter(articlesAdapter::retry)
        recyclerView.adapter = articlesAdapter.withLoadStateFooter(loadStateAdapter)

        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            articlesAdapter.submitData(viewLifecycleOwner.lifecycle, articles)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            articlesAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collectLatest {
                    if (articlesAdapter.itemCount > 0) {
                        recyclerView.scrollToPosition(0)
                    }
                }
        }

        viewModel.visibleFilterButton.observe(viewLifecycleOwner) {
            if (it) {
                val behavior = (binding.filterButton.layoutParams as CoordinatorLayout.LayoutParams).behavior
                (behavior as HideBottomViewOnScrollBehavior).slideUp(binding.filterButton)
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
    }
}
