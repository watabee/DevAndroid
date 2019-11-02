package com.github.watabee.rakutenapp.pagenation

import io.reactivex.Flowable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PagedItemsFetcherTest {

    @Test
    fun testSubscribe_thenEmitNothing() {
        val fetcher = PagedItemsFetcher<Unit, String> { _, _ -> Flowable.just(PagedItem(listOf("1"))) }
        val testSubscriber = fetcher.result.test()

        testSubscriber.assertNoValues()
        testSubscriber.assertNoErrors()
        testSubscriber.assertNotComplete()
    }

    @Test
    fun testRequest_thenSuccessAll() {
        val fetcher = PagedItemsFetcher<Unit, String> { _, page ->
            when (page) {
                1 -> Flowable.just(PagedItem(listOf("1", "2"), nextPage = 2))
                2 -> Flowable.just(PagedItem(listOf("3", "4"), nextPage = 3))
                else -> Flowable.just(PagedItem(listOf("5", "6"), nextPage = PagedItem.NO_PAGE))
            }
        }
        val testSubscriber = fetcher.result.test()

        testSubscriber.assertNoValues()
        testSubscriber.assertNoErrors()

        fetcher.request(Unit)
        testSubscriber.assertValueCount(2)
        testSubscriber.assertValueAt(0, FetchItemsResult(isLoading = true))
        testSubscriber.assertValueAt(1, FetchItemsResult((1..2).toStringList()))
        testSubscriber.assertNoErrors()

        fetcher.request(Unit)
        testSubscriber.assertValueCount(4)
        testSubscriber.assertValueAt(2, FetchItemsResult((1..2).toStringList(), isLoading = true))
        testSubscriber.assertValueAt(3, FetchItemsResult((1..4).toStringList()))
        testSubscriber.assertNoErrors()

        fetcher.request(Unit)
        testSubscriber.assertValueCount(6)
        testSubscriber.assertValueAt(4, FetchItemsResult((1..4).toStringList(), isLoading = true))
        testSubscriber.assertValueAt(5, FetchItemsResult((1..6).toStringList()))
        testSubscriber.assertNoErrors()

        fetcher.request(Unit)
        testSubscriber.assertValueCount(6)
        testSubscriber.assertNoErrors()
    }

    @Test
    fun testRequest_thenEmitError() {
        var emitError = true
        val fetcher = PagedItemsFetcher<Unit, String> { _, page ->
            when (page) {
                1 -> Flowable.just(PagedItem(listOf("1", "2"), nextPage = 2))
                2 -> Flowable.defer {
                    if (emitError) {
                        emitError = false
                        Flowable.error(RuntimeException())
                    } else {
                        Flowable.just(PagedItem(listOf("3", "4"), nextPage = 3))
                    }
                }
                else -> Flowable.just(PagedItem(listOf("5", "6"), nextPage = PagedItem.NO_PAGE))
            }
        }
        val testSubscriber = fetcher.result.test()

        testSubscriber.assertNoValues()
        testSubscriber.assertNoErrors()

        fetcher.request(Unit)
        testSubscriber.assertValueCount(2)
        testSubscriber.assertValueAt(0, FetchItemsResult(isLoading = true))
        testSubscriber.assertValueAt(1, FetchItemsResult((1..2).toStringList()))
        testSubscriber.assertNoErrors()

        fetcher.request(Unit)
        testSubscriber.assertValueCount(4)
        testSubscriber.assertValueAt(2, FetchItemsResult((1..2).toStringList(), isLoading = true))
        testSubscriber.assertValueAt(3) { result ->
            result.items == (1..2).toStringList() && result.e is RuntimeException
        }
        testSubscriber.assertNoErrors()

        fetcher.request(Unit)
        testSubscriber.assertValueCount(6)
        testSubscriber.assertValueAt(4, FetchItemsResult((1..2).toStringList(), isLoading = true))
        testSubscriber.assertValueAt(5, FetchItemsResult((1..4).toStringList()))
        testSubscriber.assertNoErrors()
    }

    @Test
    fun testRefresh_thenSuccess() {
        val fetcher = PagedItemsFetcher<Unit, String> { _, page ->
            when (page) {
                1 -> Flowable.just(PagedItem(listOf("1", "2"), nextPage = 2))
                2 -> Flowable.just(PagedItem(listOf("3", "4"), nextPage = 3))
                else -> Flowable.just(PagedItem(listOf("5", "6"), nextPage = PagedItem.NO_PAGE))
            }
        }
        val testSubscriber = fetcher.result.test()

        testSubscriber.assertNoValues()
        testSubscriber.assertNoErrors()

        fetcher.request(Unit)
        testSubscriber.assertValueCount(2)
        testSubscriber.assertValueAt(0, FetchItemsResult(isLoading = true))
        testSubscriber.assertValueAt(1, FetchItemsResult((1..2).toStringList()))
        testSubscriber.assertNoErrors()

        fetcher.request(Unit)
        testSubscriber.assertValueCount(4)
        testSubscriber.assertValueAt(2, FetchItemsResult((1..2).toStringList(), isLoading = true))
        testSubscriber.assertValueAt(3, FetchItemsResult((1..4).toStringList()))
        testSubscriber.assertNoErrors()

        fetcher.refresh(Unit)
        testSubscriber.assertValueCount(7)
        testSubscriber.assertValueAt(4, FetchItemsResult())
        testSubscriber.assertValueAt(5, FetchItemsResult(isLoading = true))
        testSubscriber.assertValueAt(6, FetchItemsResult((1..2).toStringList()))
        testSubscriber.assertNoErrors()

        fetcher.request(Unit)
        testSubscriber.assertValueCount(9)
        testSubscriber.assertValueAt(7, FetchItemsResult((1..2).toStringList(), isLoading = true))
        testSubscriber.assertValueAt(8, FetchItemsResult((1..4).toStringList()))
        testSubscriber.assertNoErrors()
    }

    private fun IntRange.toStringList(): List<String> = toList().map { it.toString() }
}
