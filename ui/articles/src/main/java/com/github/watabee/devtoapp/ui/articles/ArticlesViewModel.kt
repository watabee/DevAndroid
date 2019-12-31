package com.github.watabee.devtoapp.ui.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.github.watabee.devtoapp.data.api.DevToApi
import com.github.watabee.devtoapp.data.api.response.Article
import com.github.watabee.devtoapp.extensions.isActive
import com.github.watabee.devtoapp.pagenation.LoadMoreStatus
import com.github.watabee.devtoapp.ui.article.ArticleDetailUiModel
import com.github.watabee.devtoapp.util.CoroutineDispatchers
import com.github.watabee.devtoapp.util.Logger
import com.github.watabee.devtoapp.util.SingleLiveEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val REQUEST_PER_PAGE = 31

internal class ArticlesViewModel @Inject constructor(
    private val devToApi: DevToApi,
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

    private val _selectedArticle = SingleLiveEvent<ArticleDetailUiModel>()
    val selectedArticle: LiveData<ArticleDetailUiModel> = _selectedArticle

    @UseExperimental(ObsoleteCoroutinesApi::class)
    private val requestEvent: SendChannel<RequestEvent> = viewModelScope.actor {
        var page: Int? = 1
        var job: Job? = null
        var detailJob: Job? = null
        val articles = mutableListOf<Article>()
        val articleUiModels = mutableListOf<ArticleUiModel>()

        for (event in channel) {
            if (event is RequestEvent.SelectArticle) {
                _selectedArticle.value = articles.find { it.id == event.articleId }?.let(::ArticleDetailUiModel)
                if (detailJob.isActive()) {
                    detailJob.cancelAndJoin()
                }
                detailJob = launch {
                    try {
                        _selectedArticle.value = ArticleDetailUiModel(devToApi.findArticle(event.articleId))
                    } catch (e: Throwable) {
                        logger.e(e)
                    }
                }
                continue
            }

            val currentJob = job
            if (event == RequestEvent.Refresh) {
                if (currentJob.isActive()) {
                    currentJob.cancelAndJoin()
                }
                page = 1
                articles.clear()
                articleUiModels.clear()
                _articleUiModels.value = articleUiModels
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
                    val foundArticles: List<Article> = devToApi.findArticles(page, REQUEST_PER_PAGE)
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

    fun refresh() {
        requestEvent.offer(RequestEvent.Refresh)
    }

    fun loadMore() {
        requestEvent.offer(RequestEvent.LoadMore)
    }

    fun retry() {
        loadMore()
    }

    fun selectArticle(articleId: Int) {
        requestEvent.offer(RequestEvent.SelectArticle(articleId))
    }

    private sealed class RequestEvent {
        object Refresh : RequestEvent()

        object LoadMore : RequestEvent()

        class SelectArticle(val articleId: Int) : RequestEvent()
    }
}
