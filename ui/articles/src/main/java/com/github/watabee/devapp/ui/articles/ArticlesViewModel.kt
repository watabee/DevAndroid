package com.github.watabee.devapp.ui.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.github.watabee.devapp.data.api.DevApi
import com.github.watabee.devapp.util.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
internal class ArticlesViewModel @Inject constructor(
    private val devApi: DevApi,
    private val logger: Logger
) : ViewModel() {

    private val actionFlow = MutableSharedFlow<Action>()
    private val pagingConfig = PagingConfig(pageSize = 30, initialLoadSize = 30, prefetchDistance = 20)

    val state: StateFlow<State> = actionFlow
        .scan(Tags(null, null)) { tags, event ->
            when (event) {
                Action.Refresh -> Tags(currentTag = null, prevTag = tags.currentTag)
                is Action.FilterByTag -> Tags(currentTag = event.tag, prevTag = tags.currentTag)
            }
        }
        .flatMapLatest { tags ->
            Pager(config = pagingConfig, initialKey = 1) { ArticleDataSource(devApi, tags.currentTag, logger) }
                .flow
                .map { pagingData -> pagingData.map(::ArticleUiModel) }
                .cachedIn(viewModelScope)
                .map { State(it, tags.currentTag, isTagChanged = tags.currentTag != tags.prevTag) }
        }
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = State())

    init {
        dispatchAction(Action.Refresh)
    }

    private fun dispatchAction(action: Action) {
        viewModelScope.launch {
            actionFlow.emit(action)
        }
    }

    fun filterByTag(tag: String) {
        dispatchAction(Action.FilterByTag(tag))
    }

    fun resetFilter() {
        dispatchAction(Action.Refresh)
    }

    data class State(
        val articles: PagingData<ArticleUiModel> = PagingData.empty(),
        val selectedTag: String? = null,
        val isTagChanged: Boolean = false
    ) {
        val visibleFilterButton: Boolean get() = !selectedTag.isNullOrEmpty()
    }

    sealed class Action {
        object Refresh : Action()

        class FilterByTag(val tag: String) : Action()
    }

    private data class Tags(val currentTag: String?, val prevTag: String?)
}
