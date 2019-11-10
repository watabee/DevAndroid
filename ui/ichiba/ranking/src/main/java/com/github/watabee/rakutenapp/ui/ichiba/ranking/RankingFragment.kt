package com.github.watabee.rakutenapp.ui.ichiba.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.watabee.rakutenapp.pagenation.FetchItemsResult
import com.github.watabee.rakutenapp.ui.ichiba.ranking.databinding.FragmentRankingBinding
import com.github.watabee.rakutenapp.util.ViewModelFactory
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import javax.inject.Inject

class RankingFragment @Inject constructor(
    private val factory: ViewModelFactory.Factory
) : Fragment() {

    private val viewModel by viewModels<RankingViewModel> { factory.create(this, null) }
    private lateinit var binding: FragmentRankingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRankingBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GroupAdapter<GroupieViewHolder>()

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        recyclerView.adapter = adapter

        val swipeRefreshLayout: SwipeRefreshLayout = binding.swipeRefreshLayout

        viewModel.result.observe(viewLifecycleOwner) { result: FetchItemsResult<RankingUiModel> ->
            adapter.update(result.items.map(::RankingBindableItem))
            if (!result.isLoading) {
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }
}
