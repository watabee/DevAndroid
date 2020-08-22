package com.github.watabee.devapp.ui.articles

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.github.watabee.devapp.data.api.DevApi
import com.github.watabee.devapp.util.Logger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan

@OptIn(ExperimentalCoroutinesApi::class)
internal class ArticlesViewModel @ViewModelInject constructor(
    private val devApi: DevApi,
    private val logger: Logger
) : ViewModel() {

    private val requestEvent = MutableStateFlow<RequestEvent>(RequestEvent.Refresh)
    private val pagingConfig = PagingConfig(pageSize = 30, initialLoadSize = 30, prefetchDistance = 20)

    private val searchResult: LiveData<Pair<PagingData<ArticleUiModel>, String?>> = requestEvent
        .scan<RequestEvent, String?>(null) { tag, event ->
            when (event) {
                RequestEvent.Refresh -> tag
                is RequestEvent.FilterByTag -> event.tag
                RequestEvent.ResetFilter -> null
            }
        }
        .flatMapLatest { tag ->
            Pager(config = pagingConfig, initialKey = 1) { ArticleDataSource(devApi, tag, logger) }
                .flow
                .map { pagingData -> pagingData.map(::ArticleUiModel) }
                .cachedIn(viewModelScope)
                .map { it to tag }
        }
        .asLiveData()

    val articles: LiveData<PagingData<ArticleUiModel>> = searchResult.map { it.first }
    val selectedTag: LiveData<String?> = searchResult.map { it.second }
    val visibleFilterButton: LiveData<Boolean> = selectedTag.map { !it.isNullOrEmpty() }

    fun filterByTag(tag: String) {
        requestEvent.value = RequestEvent.FilterByTag(tag)
    }

    fun resetFilter() {
        requestEvent.value = RequestEvent.ResetFilter
    }

    private sealed class RequestEvent {
        object Refresh : RequestEvent()

        class FilterByTag(val tag: String) : RequestEvent()

        object ResetFilter : RequestEvent()
    }
}
