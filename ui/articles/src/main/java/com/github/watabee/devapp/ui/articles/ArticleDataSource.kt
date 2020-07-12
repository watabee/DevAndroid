package com.github.watabee.devapp.ui.articles

import androidx.paging.PagingSource
import com.github.watabee.devapp.data.Article
import com.github.watabee.devapp.data.api.DevApi
import com.github.watabee.devapp.util.Logger

internal class ArticleDataSource(
    private val devApi: DevApi,
    private val tag: String?,
    private val logger: Logger
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        val perPage = params.loadSize + 1

        return try {
            val foundArticles = devApi.findArticles(page = page, perPage = perPage, tag = tag)
            val nextKey = if (foundArticles.size < perPage) null else page + 1
            logger.e("nextKey = $nextKey")

            LoadResult.Page(data = foundArticles.take(perPage - 1), prevKey = null, nextKey = nextKey)
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
}
