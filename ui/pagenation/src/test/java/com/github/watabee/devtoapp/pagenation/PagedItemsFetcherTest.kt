package com.github.watabee.devtoapp.pagenation

import com.github.watabee.devtoapp.pagenation.FetchItemsResult.LoadState
import com.github.watabee.devtoapp.util.CoroutineTestRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@UseExperimental(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class PagedItemsFetcherTest {

    @get:Rule val testRule = CoroutineTestRule()

    @Test
    fun test_whenNoRequest_thenEmitNothing() {
        val fetcher = createFetcher()
        assertThat(fetcher.result.isEmpty).isTrue()
    }

    @Test
    fun test_whenRequests_thenSuccessAll() {
        val fetcher = createFetcher()
        val result = fetcher.result

        fetcher.request(Unit)
        assertThat(result.poll()).isEqualTo(FetchItemsResult<String>(loadState = LoadState.INITIAL_LOAD))
        testRule.advanceTimeBy(1000L)
        assertThat(result.poll()).isEqualTo(FetchItemsResult(items = listOf("1")))

        fetcher.request(Unit)
        assertThat(result.poll()).isEqualTo(FetchItemsResult(items = listOf("1"), loadState = LoadState.LOAD_MORE))
        testRule.advanceTimeBy(1000L)
        assertThat(result.poll()).isEqualTo(FetchItemsResult(items = listOf("1", "2")))

        fetcher.request(Unit)
        assertThat(result.poll()).isEqualTo(FetchItemsResult(items = listOf("1", "2"), loadState = LoadState.LOAD_MORE))
        testRule.advanceTimeBy(1000L)
        assertThat(result.poll()).isEqualTo(FetchItemsResult(items = listOf("1", "2", "3")))

        // If next page is invalid, request is ignored.
        fetcher.request(Unit)
        assertThat(result.poll()).isNull()
    }

    @Test
    fun test_whenRequests_thenThrowError() {
        val fetcher = createFetcher(emitError = true)
        val result = fetcher.result

        fetcher.request(Unit)
        assertThat(result.poll()).isEqualTo(FetchItemsResult<String>(loadState = LoadState.INITIAL_LOAD))
        testRule.advanceTimeBy(1000L)
        assertThat(result.poll()).isEqualTo(FetchItemsResult(items = listOf("1")))

        fetcher.request(Unit)
        assertThat(result.poll()).isEqualTo(FetchItemsResult(items = listOf("1"), loadState = LoadState.LOAD_MORE))
        testRule.advanceTimeBy(1000L)
        result.poll().let {
            assertThat(it?.items).isEqualTo(listOf("1"))
            assertThat(it?.e).isInstanceOf(RuntimeException::class.java)
        }

        fetcher.request(Unit)
        assertThat(result.poll()).isEqualTo(FetchItemsResult(items = listOf("1"), loadState = LoadState.LOAD_MORE))
        testRule.advanceTimeBy(1000L)
        assertThat(result.poll()).isEqualTo(FetchItemsResult(items = listOf("1", "2")))
    }

    @Test
    fun test_whenRefresh_thenSuccessReload() {
        val fetcher = createFetcher()
        val result = fetcher.result

        fetcher.request(Unit)
        assertThat(result.poll()).isEqualTo(FetchItemsResult<String>(loadState = LoadState.INITIAL_LOAD))
        testRule.advanceTimeBy(1000L)
        assertThat(result.poll()).isEqualTo(FetchItemsResult(items = listOf("1")))

        fetcher.refresh(Unit)
        assertThat(result.poll()).isEqualTo(FetchItemsResult<String>(loadState = LoadState.INITIAL_LOAD))
        testRule.advanceTimeBy(1000L)
        assertThat(result.poll()).isEqualTo(FetchItemsResult(items = listOf("1")))
    }

    @Test
    fun test_whenRequestWhileLoading_thenEmitNothing() {
        val fetcher = createFetcher()
        val result = fetcher.result

        fetcher.request(Unit)
        assertThat(result.poll()).isEqualTo(FetchItemsResult<String>(loadState = LoadState.INITIAL_LOAD))
        testRule.advanceTimeBy(500L)

        // Ignore request if loading...
        fetcher.request(Unit)
        assertThat(result.poll()).isNull()

        testRule.advanceTimeBy(500L)
        assertThat(result.poll()).isEqualTo(FetchItemsResult(items = listOf("1")))
    }

    @Test
    fun test_whenRefreshWhileLoading_thenSuccessReload() {
        val fetcher = createFetcher()
        val result = fetcher.result

        fetcher.request(Unit)
        assertThat(result.poll()).isEqualTo(FetchItemsResult<String>(loadState = LoadState.INITIAL_LOAD))
        testRule.advanceTimeBy(500L)

        fetcher.refresh(Unit)
        assertThat(result.poll()).isEqualTo(FetchItemsResult<String>(loadState = LoadState.INITIAL_LOAD))
        testRule.advanceTimeBy(1000L)
        assertThat(result.poll()).isEqualTo(FetchItemsResult(items = listOf("1")))
    }

    @Test
    fun test_whenCancelParentCoroutine_thenChannelIsClosed() {
        val fetcher = createFetcher()
        val result = fetcher.result

        fetcher.request(Unit)
        assertThat(result.poll()).isEqualTo(FetchItemsResult<String>(loadState = LoadState.INITIAL_LOAD))
        testRule.advanceTimeBy(500L)

        testRule.cancel()
        assertThat(result.isClosedForReceive).isTrue()
    }

    private fun createFetcher(emitError: Boolean = false): PagedItemsFetcher<Unit, String> {
        var error = emitError
        return PagedItemsFetcher(coroutineScope = testRule) { _, page ->
            delay(1000L)
            when (page) {
                1 -> PagedItem(items = listOf("1"), nextPage = 2)
                2 -> {
                    if (error) {
                        error = false
                        throw RuntimeException()
                    } else {
                        PagedItem(items = listOf("2"), nextPage = 3)
                    }
                }
                else -> PagedItem(items = listOf("3"), nextPage = PagedItem.NO_PAGE)
            }
        }
    }
}
