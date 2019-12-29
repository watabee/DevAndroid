package com.github.watabee.devtoapp.ui.articles

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.watabee.devtoapp.pagenation.LoadMoreAdapter
import com.github.watabee.devtoapp.ui.articles.databinding.FragmentArticlesBinding
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class ArticlesFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory
) : Fragment(R.layout.fragment_articles) {

    private val viewModel: ArticlesViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = LoadMoreAdapter { viewModel.retry() }

        val binding = FragmentArticlesBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

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
