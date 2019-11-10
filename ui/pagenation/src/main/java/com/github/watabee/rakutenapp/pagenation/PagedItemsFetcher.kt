package com.github.watabee.rakutenapp.pagenation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch

data class FetchItemsResult<T : Any>(
    val items: List<T> = emptyList(),
    val e: Throwable? = null,
    val isLoading: Boolean = false
)

@UseExperimental(ExperimentalCoroutinesApi::class)
class PagedItemsFetcher<P : Any, R : Any>(
    firstPage: Int = 1,
    coroutineScope: CoroutineScope,
    fetchItemsLogic: suspend (param: P, page: Int) -> PagedItem<R>
) {
    private val requestEvent = Channel<Pair<Boolean, P>>()

    private val _result = ConflatedBroadcastChannel<FetchItemsResult<R>>()
    val result: ReceiveChannel<FetchItemsResult<R>> get() = _result.openSubscription()

    private var page: Int = firstPage

    init {
        coroutineScope.launch {
            var r = FetchItemsResult<R>()
            fun sendResult(newResult: FetchItemsResult<R>) {
                r = newResult
                _result.offer(newResult)
            }

            suspend fun fetch(param: P, items: List<R>) {
                if (page == PagedItem.NO_PAGE) {
                    return
                }
                sendResult(r.copy(e = null, isLoading = true))
                try {
                    val pagedItem = fetchItemsLogic(param, page)
                    page = pagedItem.nextPage
                    sendResult(FetchItemsResult(items + pagedItem.items))
                } catch (e: Throwable) {
                    sendResult(FetchItemsResult(items, e, false))
                }
            }

            for ((isRefresh, param) in requestEvent) {
                if (isRefresh) {
                    page = firstPage
                    fetch(param, emptyList())
                } else {
                    fetch(param, r.items)
                }
            }
        }.invokeOnCompletion {
            requestEvent.close()
            _result.close()
        }
    }

    fun request(param: P) {
        requestEvent.offer(false to param)
    }

    fun refresh(param: P) {
        requestEvent.offer(true to param)
    }
}
