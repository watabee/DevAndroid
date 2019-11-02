package com.github.watabee.rakutenapp.pagenation

import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.withLatestFrom

data class FetchItemsResult<T : Any>(
    val items: List<T> = emptyList(),
    val e: Throwable? = null,
    val isLoading: Boolean = false
)

class PagedItemsFetcher<P : Any, R : Any>(
    firstPage: Int = 1,
    fetchItemsLogic: (param: P, page: Int) -> Flowable<PagedItem<R>>
) {
    private val _result = BehaviorProcessor.createDefault(FetchItemsResult<R>())
    private val page = BehaviorProcessor.createDefault(firstPage)

    // Input
    private val requestEvent = PublishProcessor.create<P>()
    private val refreshEvent = PublishProcessor.create<P>()

    // Output
    val result: Flowable<FetchItemsResult<R>> = _result.skip(1) // Skip default value

    init {
        refreshEvent.map { firstPage }.subscribe(page)
        refreshEvent.map { FetchItemsResult<R>() }.subscribe(_result)

        refreshEvent.subscribe(requestEvent)

        val state: Flowable<State> = requestEvent.withLatestFrom(page)
            .switchMap { (param: P, page: Int) ->
                if (page < 0) {
                    Flowable.never()
                } else {
                    fetchItemsLogic(param, page)
                        .map<State> { (items, nextPage) -> State.Success(items, nextPage) }
                        .onErrorReturn(State::Failure)
                        .startWith(State.Loading)
                }
            }
            .replay(1)
            .refCount()

        @Suppress("UNCHECKED_CAST") val success =
            state.ofType(State.Success::class.java).share() as Flowable<State.Success<R>>
        success.map { it.nextPage }.subscribe(page)

        success.map { it.items }.withLatestFrom(_result) { items, prevResult ->
            FetchItemsResult(prevResult.items + items)
        }.subscribe(_result)

        state.ofType(State.Failure::class.java)
            .map { it.e }
            .withLatestFrom(_result) { throwable, prevResult ->
                FetchItemsResult(items = prevResult.items, e = throwable)
            }
            .subscribe(_result)

        state.ofType(State.Loading::class.java)
            .withLatestFrom(_result) { _, prevResult ->
                FetchItemsResult(items = prevResult.items, e = null, isLoading = true)
            }
            .subscribe(_result)
    }

    fun request(param: P): Unit = requestEvent.onNext(param)

    fun refresh(param: P): Unit = refreshEvent.onNext(param)

    private sealed class State {
        object Loading : State()

        data class Success<T : Any>(val items: List<T>, val nextPage: Int) : State()

        data class Failure(val e: Throwable) : State()
    }
}