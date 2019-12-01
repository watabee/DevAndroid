package com.github.watabee.rakutenapp.ui.ichiba.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.github.watabee.rakutenapp.pagenation.LoadMoreAdapter
import com.github.watabee.rakutenapp.ui.ichiba.ranking.databinding.FragmentRankingBinding
import com.github.watabee.rakutenapp.ui.signin.SignInFragment
import javax.inject.Inject

class RankingFragment @Inject constructor(
    private val factory: ViewModelProvider.Factory
) : Fragment() {

    private val viewModel by viewModels<RankingViewModel> { factory }
    private lateinit var binding: FragmentRankingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRankingBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = LoadMoreAdapter(retry = viewModel::request)

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        recyclerView.adapter = adapter

        viewModel.uiModels.observe(viewLifecycleOwner) { uiModels: List<RankingUiModel> ->
            adapter.update(uiModels.map { RankingBindableItem(it, viewModel::onFavoriteButtonClicked) })
        }

        viewModel.loadMoreStatus.observe(viewLifecycleOwner) { status ->
            adapter.status = status
        }

        viewModel.openSignInView.observe(viewLifecycleOwner) {
            SignInFragment.openSignInView(requireActivity().supportFragmentManager)
        }
    }
}
