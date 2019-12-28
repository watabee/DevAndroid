package com.github.watabee.devtoapp.ui.ichiba.ranking

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.github.watabee.devtoapp.data.api.IchibaItemApi
import com.github.watabee.devtoapp.data.api.response.FindRankingItemsResponse
import com.github.watabee.devtoapp.data.db.daos.FavoriteIchibaItemDao
import com.github.watabee.devtoapp.data.db.entities.FavoriteIchibaItem
import com.github.watabee.devtoapp.pagenation.FetchItemsResult
import com.github.watabee.devtoapp.pagenation.FetchItemsResult.LoadState
import com.github.watabee.devtoapp.pagenation.LoadMoreStatus
import com.github.watabee.devtoapp.pagenation.PagedItem
import com.github.watabee.devtoapp.pagenation.PagedItemsFetcher
import com.github.watabee.devtoapp.util.CoroutineDispatchers
import com.github.watabee.devtoapp.util.Logger
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val MAX_PAGE = 34

internal class RankingViewModel @Inject constructor(
    private val ichibaItemApi: IchibaItemApi,
    private val coroutineDispatchers: CoroutineDispatchers,
    private val favoriteIchibaItemDao: FavoriteIchibaItemDao,
    private val logger: Logger
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

    val uiModels: LiveData<List<RankingUiModel>> = liveData {
        result.asFlow()
            .combine(favoriteIchibaItemDao.getItemCodes()) { result, favoriteItems ->
                result.items.map {
                    val isFavorite = favoriteItems.contains(it.itemCode)
                    if (it.isFavorite != isFavorite) it.copy(isFavorite = isFavorite) else it
                }
            }
            .catch { logger.e(it) }
            .collect { emit(it) }
    }

    val initialLoad: LiveData<Boolean> = result.map { it.loadState == LoadState.INITIAL_LOAD }

    val loadMoreStatus: LiveData<LoadMoreStatus> = result.map {
        when {
            it.loadState == LoadState.LOAD_MORE -> LoadMoreStatus.LOADING
            it.e != null -> LoadMoreStatus.ERROR
            else -> LoadMoreStatus.IDLE
        }
    }

    init {
        request()
    }

    fun request() = fetcher.request(Unit)

    fun refresh() = fetcher.refresh(Unit)

    fun onFavoriteButtonClicked(uiModel: RankingUiModel) {
        viewModelScope.launch {
            if (!uiModel.isFavorite) {
                favoriteIchibaItemDao.add(FavoriteIchibaItem(uiModel.itemCode, uiModel.itemName, uiModel.imageUrl, uiModel.itemPrice))
            } else {
                favoriteIchibaItemDao.delete(uiModel.itemCode)
            }
        }
    }

    private fun List<FindRankingItemsResponse.Item>.toUiModels(): List<RankingUiModel> =
        map {
            RankingUiModel(
                itemCode = it.itemCode,
                itemName = it.itemName,
                imageUrl = it.mediumImageUrls.firstOrNull(),
                itemPrice = it.itemPrice,
                isFavorite = false
            )
        }
}
