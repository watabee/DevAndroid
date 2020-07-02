package com.github.watabee.devapp.ui.articles

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.github.watabee.devapp.data.api.DevApi
import com.github.watabee.devapp.data.api.response.Article
import com.github.watabee.devapp.extensions.isActive
import com.github.watabee.devapp.pagenation.LoadMoreStatus
import com.github.watabee.devapp.util.CoroutineDispatchers
import com.github.watabee.devapp.util.Logger
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val REQUEST_PER_PAGE = 31

internal class ArticlesViewModel @ViewModelInject constructor(
    private val devApi: DevApi,
    private val dispatchers: CoroutineDispatchers,
    private val logger: Logger
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading.distinctUntilChanged()

    private val _isError = MutableLiveData<Boolean>(false)
    val isError: LiveData<Boolean> = _isError.distinctUntilChanged()

    private val _loadMoreStatus = MutableLiveData<LoadMoreStatus>(LoadMoreStatus.IDLE)
    val loadMoreStatus: LiveData<LoadMoreStatus> = _loadMoreStatus.distinctUntilChanged()

    private val _articleUiModels = MutableLiveData<List<ArticleUiModel>>(emptyList())
    val articleUiModels: LiveData<List<ArticleUiModel>> = _articleUiModels

    private val _openArticleDetail = LiveEvent<com.github.watabee.devapp.data.Article>()
    val openArticleDetail: LiveData<com.github.watabee.devapp.data.Article> = _openArticleDetail

    private val _selectedTag = MutableLiveData<String>()
    val selectedTag: LiveData<String> = _selectedTag

    private val _visibleFilterButton = MutableLiveData<Boolean>(true)
    val visibleFilterButton: LiveData<Boolean> = _visibleFilterButton.switchMap { visible ->
        _selectedTag.map { tag -> if (!visible) false else !tag.isNullOrEmpty() }
    }

    @Suppress("LoopWithTooManyJumpStatements")
    @OptIn(ObsoleteCoroutinesApi::class)
    private val requestEvent: SendChannel<RequestEvent> = viewModelScope.actor {
        var page: Int? = 1
        var tag: String? = null
        var job: Job? = null
        val articles = mutableListOf<Article>()
        val articleUiModels = mutableListOf<ArticleUiModel>()

        suspend fun reset(currentJob: Job?, newTag: String?) {
            if (currentJob.isActive()) {
                currentJob.cancelAndJoin()
            }
            page = 1
            tag = newTag
            articles.clear()
            articleUiModels.clear()
            _articleUiModels.value = articleUiModels
            _selectedTag.value = tag
        }

        eventloop@ for (event in channel) {
            if (event is RequestEvent.SelectArticle) {
                val article = articles.find { it.id == event.articleId }
                if (article != null) {
                    _openArticleDetail.value = article
                }
                continue
            }

            val currentJob = job
            when (event) {
                RequestEvent.Refresh -> reset(currentJob, newTag = tag)
                is RequestEvent.FilterByTag -> {
                    if (tag == event.tag) {
                        continue@eventloop
                    }
                    reset(currentJob, newTag = event.tag)
                }
                RequestEvent.ResetFilter -> reset(currentJob, newTag = null)
            }
            val currentPage = page
            if (currentPage == null || currentJob.isActive()) {
                continue
            }
            job = launch {
                _isError.value = false
                if (page == 1) {
                    _isLoading.value = true
                } else {
                    _loadMoreStatus.value = LoadMoreStatus.LOADING
                }

                try {
                    val foundArticles: List<Article> = devApi.findArticles(page = page, perPage = REQUEST_PER_PAGE, tag = tag)
                    val foundArticlesCount = foundArticles.size
                    val defaultSizeArticles = foundArticles.take(REQUEST_PER_PAGE - 1)

                    val uiModels = withContext(dispatchers.computation) { defaultSizeArticles.map(::ArticleUiModel) }
                    articles.addAll(defaultSizeArticles)
                    articleUiModels.addAll(uiModels)
                    _articleUiModels.value = articleUiModels
                    _loadMoreStatus.value = LoadMoreStatus.IDLE
                    _isLoading.value = false
                    page = if (foundArticlesCount < REQUEST_PER_PAGE) null else currentPage + 1
                } catch (e: Throwable) {
                    logger.e(e)
                    if (e !is CancellationException) {
                        if (currentPage == 1) {
                            _isLoading.value = false
                            _isError.value = true
                        } else {
                            _loadMoreStatus.value = LoadMoreStatus.ERROR
                        }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        requestEvent.close()
    }

    fun refresh() {
        requestEvent.offer(RequestEvent.Refresh)
    }

    fun loadMore() {
        requestEvent.offer(RequestEvent.LoadMore)
    }

    fun selectArticle(articleId: Int) {
        requestEvent.offer(RequestEvent.SelectArticle(articleId))
    }

    fun filterByTag(tag: String) {
        requestEvent.offer(RequestEvent.FilterByTag(tag))
    }

    fun resetFilter() {
        requestEvent.offer(RequestEvent.ResetFilter)
    }

    fun showFilterButton(shouldShow: Boolean) {
        _visibleFilterButton.value = shouldShow
    }

    fun retry() {
        loadMore()
    }

    private sealed class RequestEvent {
        object Refresh : RequestEvent()

        object LoadMore : RequestEvent()

        class SelectArticle(val articleId: Int) : RequestEvent()

        class FilterByTag(val tag: String) : RequestEvent()

        object ResetFilter : RequestEvent()
    }
}
