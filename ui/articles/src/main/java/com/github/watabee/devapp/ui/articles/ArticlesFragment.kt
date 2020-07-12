package com.github.watabee.devapp.ui.articles

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
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
import com.github.watabee.devapp.ui.article.removeArticleFragment
import com.github.watabee.devapp.ui.articles.databinding.FragmentArticlesBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
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

        val onBackPressedCallback = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                if (childFragmentManager.removeArticleFragment()) {
                    isEnabled = false
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        val articlesAdapter = ArticlesAdapter(
            onItemClicked = { article ->
                childFragmentManager.addArticleFragment(fragmentContainerView.id, article)
                onBackPressedCallback.isEnabled = true
            },
            onTagClicked = viewModel::filterByTag
        )

        val loadStateAdapter = LoadStateAdapter(articlesAdapter::retry)
        recyclerView.adapter = articlesAdapter.withLoadStateFooter(loadStateAdapter)

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
    }
}
