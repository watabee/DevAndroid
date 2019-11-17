package com.github.watabee.rakutenapp.pagenation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch

data class FetchItemsResult<T : Any>(
    val items: List<T> = emptyList(),
    val e: Throwable? = null,
    val loadState: LoadState = LoadState.NONE
) {
    enum class LoadState {
        NONE, INITIAL_LOAD, LOAD_MORE
    }
}

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
            var prevResult = FetchItemsResult<R>()
            var job: Job? = null
            val supervisorJob = SupervisorJob()

            fun sendResult(newResult: FetchItemsResult<R>) {
                prevResult = newResult
                _result.offer(newResult)
            }

            for ((isRefresh, param) in requestEvent) {
                if (isRefresh) {
                    page = firstPage
                    if (job != null && job.isActive) {
                        job.cancel()
                    }
                }
                if (page == PagedItem.NO_PAGE) {
                    continue
                }
                if (job != null && job.isActive) {
                    continue
                }
                job = launch(supervisorJob) {
                    try {
                        val loadState =
                            if (page == firstPage) FetchItemsResult.LoadState.INITIAL_LOAD else FetchItemsResult.LoadState.LOAD_MORE
                        sendResult(
                            FetchItemsResult(if (isRefresh) emptyList() else prevResult.items, loadState = loadState)
                        )
                        val pagedItem = fetchItemsLogic(param, page)
                        page = pagedItem.nextPage
                        sendResult(FetchItemsResult(prevResult.items + pagedItem.items))
                    } catch (e: Throwable) {
                        if (e !is CancellationException) {
                            sendResult(FetchItemsResult(prevResult.items, e))
                        }
                    }
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
