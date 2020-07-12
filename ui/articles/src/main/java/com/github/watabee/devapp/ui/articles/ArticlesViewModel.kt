package com.github.watabee.devapp.ui.articles

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.watabee.devapp.data.api.DevApi
import com.github.watabee.devapp.util.Logger
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ArticlesViewModel @ViewModelInject constructor(
    private val devApi: DevApi,
    private val logger: Logger
) : ViewModel() {

    private val _searchResult = MutableLiveData<Flow<PagingData<ArticleUiModel>>>()
    val searchResult: LiveData<Flow<PagingData<ArticleUiModel>>> = _searchResult

    private val _selectedTag = MutableLiveData<String>()
    val selectedTag: LiveData<String> = _selectedTag

    val visibleFilterButton: LiveData<Boolean> = _selectedTag.map { !it.isNullOrEmpty() }

    @Suppress("LoopWithTooManyJumpStatements")
    @OptIn(ObsoleteCoroutinesApi::class)
    private val requestEvent: SendChannel<RequestEvent> = viewModelScope.actor {
        var tag: String? = null
        var currentResult: Flow<PagingData<ArticleUiModel>>? = null

        eventloop@ for (event in channel) {
            tag = when (event) {
                RequestEvent.Refresh -> tag
                is RequestEvent.FilterByTag -> {
                    val lastResult = currentResult
                    if (tag == event.tag && lastResult != null) {
                        continue@eventloop
                    }
                    event.tag
                }
                RequestEvent.ResetFilter -> {
                    null
                }
            }
            _selectedTag.value = tag

            val newResult = Pager(
                config = PagingConfig(pageSize = 30, initialLoadSize = 30, prefetchDistance = 20),
                initialKey = 1
            ) { ArticleDataSource(devApi, tag, logger) }
                .flow
                .map { pagingData -> pagingData.map(::ArticleUiModel) }
                .cachedIn(viewModelScope)

            currentResult = newResult

            _searchResult.value = newResult
        }
    }

    init {
        requestEvent.offer(RequestEvent.Refresh)
    }

    override fun onCleared() {
        super.onCleared()
        requestEvent.close()
    }

    fun filterByTag(tag: String) {
        requestEvent.offer(RequestEvent.FilterByTag(tag))
    }

    fun resetFilter() {
        requestEvent.offer(RequestEvent.ResetFilter)
    }

    private sealed class RequestEvent {
        object Refresh : RequestEvent()

        class FilterByTag(val tag: String) : RequestEvent()

        object ResetFilter : RequestEvent()
    }
}
