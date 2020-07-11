package com.github.watabee.devapp.ui.articles

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ConcatAdapter
import com.github.watabee.devapp.pagenation.LoadingStateAdapter
import com.github.watabee.devapp.ui.article.ArticleFragment
import com.github.watabee.devapp.ui.articles.databinding.FragmentArticlesBinding
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import me.saket.inboxrecyclerview.InboxRecyclerView
import me.saket.inboxrecyclerview.page.ExpandablePageLayout
import me.saket.inboxrecyclerview.page.PageStateChangeCallbacks

@AndroidEntryPoint
internal class ArticlesFragment : Fragment(R.layout.fragment_articles) {

    private val viewModel: ArticlesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loadingStateAdapter = LoadingStateAdapter(viewModel::retry).apply {
            setHasStableIds(true)
        }
        val articlesAdapter = GroupAdapter<GroupieViewHolder>().apply {
            setHasStableIds(true)
            setOnItemClickListener { item, _ -> viewModel.selectArticle(item.id.toInt()) }
        }

        val config = ConcatAdapter.Config.Builder()
            .setStableIdMode(ConcatAdapter.Config.StableIdMode.ISOLATED_STABLE_IDS)
            .build()
        val adapter = ConcatAdapter(config, articlesAdapter, loadingStateAdapter)

        val binding = FragmentArticlesBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val expandablePage: ExpandablePageLayout = binding.expandablePageLayout
        val recyclerView: InboxRecyclerView = binding.recyclerView

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

        viewModel.openArticleDetail.observe(viewLifecycleOwner) { article ->
            childFragmentManager.commitNow(allowStateLoss = true) {
                replace(expandablePage.id, ArticleFragment.newInstance(article))
            }

            recyclerView.expandItem(article.id.toLong())
        }

        viewModel.articleUiModels.observe(viewLifecycleOwner) { articleUiModels ->
            articlesAdapter.update(articleUiModels.map { ArticleBindableItem(it, viewModel::filterByTag) })
        }

        viewModel.loadMoreStatus.observe(viewLifecycleOwner) { loadMoreStatus ->
            loadingStateAdapter.status = loadMoreStatus
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
