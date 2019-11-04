package com.github.watabee.rakutenapp.ui.ichiba.ranking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.watabee.rakutenapp.data.api.IchibaItemApi
import com.github.watabee.rakutenapp.data.api.response.FindRankingItemsResponse
import com.github.watabee.rakutenapp.pagenation.FetchItemsResult
import com.github.watabee.rakutenapp.pagenation.PagedItem
import com.github.watabee.rakutenapp.pagenation.PagedItemsFetcher
import com.github.watabee.rakutenapp.util.AppViewModelFactory
import com.github.watabee.rakutenapp.util.Logger
import com.github.watabee.rakutenapp.util.SchedulerProvider
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

internal class RankingViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle,
    private val ichibaItemApi: IchibaItemApi,
    private val schedulerProvider: SchedulerProvider,
    private val logger: Logger
) : ViewModel() {

    private val fetcher = PagedItemsFetcher<Unit, RankingUiModel>(1) { _, page ->
        ichibaItemApi.findRankingItems(page)
            .toFlowable()
            .map { response: FindRankingItemsResponse ->
                PagedItem(response.items.toUiModels(), nextPage = if (page >= 34) PagedItem.NO_PAGE else page + 1)
            }
    }

    private val disposable = CompositeDisposable()

    private val _result = MutableLiveData<FetchItemsResult<RankingUiModel>>()
    val result: LiveData<FetchItemsResult<RankingUiModel>> = _result

    @AssistedInject.Factory
    interface Factory : AppViewModelFactory<RankingViewModel> {
        override fun create(handle: SavedStateHandle): RankingViewModel
    }

    init {
        fetcher.result
            .observeOn(schedulerProvider.main)
            .subscribeBy(onNext = _result::setValue, onError = logger::e)
            .addTo(disposable)

        request()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun request() = fetcher.request(Unit)

    fun refresh() = fetcher.refresh(Unit)

    private fun List<FindRankingItemsResponse.Item>.toUiModels(): List<RankingUiModel> =
        map {
            RankingUiModel(itemCode = it.itemCode, itemName = it.itemName)
        }
}
