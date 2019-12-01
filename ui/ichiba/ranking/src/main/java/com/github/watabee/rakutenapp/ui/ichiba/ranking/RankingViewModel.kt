package com.github.watabee.rakutenapp.ui.ichiba.ranking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.github.watabee.rakutenapp.auth.AuthRepository
import com.github.watabee.rakutenapp.data.api.IchibaItemApi
import com.github.watabee.rakutenapp.data.api.response.FindRankingItemsResponse
import com.github.watabee.rakutenapp.pagenation.FetchItemsResult
import com.github.watabee.rakutenapp.pagenation.FetchItemsResult.LoadState
import com.github.watabee.rakutenapp.pagenation.LoadMoreStatus
import com.github.watabee.rakutenapp.pagenation.PagedItem
import com.github.watabee.rakutenapp.pagenation.PagedItemsFetcher
import com.github.watabee.rakutenapp.util.CoroutineDispatchers
import com.github.watabee.rakutenapp.util.SingleLiveEvent
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val MAX_PAGE = 34

internal class RankingViewModel @Inject constructor(
    private val ichibaItemApi: IchibaItemApi,
    private val coroutineDispatchers: CoroutineDispatchers,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val fetcher = PagedItemsFetcher<Unit, RankingUiModel>(1, viewModelScope) { _, page ->
        withContext(coroutineDispatchers.io) {
            val response = ichibaItemApi.findRankingItems(page)
            PagedItem(response.items.toUiModels(), nextPage = if (page >= MAX_PAGE) PagedItem.NO_PAGE else page + 1)
        }
    }

    private val result: LiveData<FetchItemsResult<RankingUiModel>> = liveData {
        for (result in fetcher.result) {
            emit(result)
        }
    }

    private val favoriteButtonClickedEvent = MutableLiveData<RankingUiModel>()

    val uiModels: LiveData<List<RankingUiModel>> = MediatorLiveData<List<RankingUiModel>>().apply {
        addSource(result) { value = it.items }
        addSource(favoriteButtonClickedEvent) { uiModel ->
            value = value?.map {
                if (it.itemCode == uiModel.itemCode) it.copy(isFavorite = !it.isFavorite) else it
            }
        }
    }

    val initialLoad: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(result) { value = it.loadState == LoadState.INITIAL_LOAD }
    }

    val loadMoreStatus: LiveData<LoadMoreStatus> = MediatorLiveData<LoadMoreStatus>().apply {
        addSource(result) {
            value = when {
                it.loadState == LoadState.LOAD_MORE -> LoadMoreStatus.LOADING
                it.e != null -> LoadMoreStatus.ERROR
                else -> LoadMoreStatus.IDLE
            }
        }
    }

    private val _openSignInView = SingleLiveEvent<Unit>()
    val openSignInView: LiveData<Unit> = _openSignInView

    init {
        request()
    }

    fun request() = fetcher.request(Unit)

    fun refresh() = fetcher.refresh(Unit)

    fun onFavoriteButtonClicked(uiModel: RankingUiModel) {
        if (authRepository.isSignedIn()) {
            favoriteButtonClickedEvent.value = uiModel
        } else {
            _openSignInView.value = Unit
        }
    }

    private fun List<FindRankingItemsResponse.Item>.toUiModels(): List<RankingUiModel> =
        map {
            RankingUiModel(itemCode = it.itemCode, itemName = it.itemName, imageUrl = it.mediumImageUrls.firstOrNull(), isFavorite = false)
        }
}
