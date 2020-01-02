package com.github.watabee.devtoapp.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.github.watabee.devtoapp.data.api.DevToApi
import com.github.watabee.devtoapp.data.db.daos.ArticleDao
import com.github.watabee.devtoapp.util.Logger
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

internal class ArticleViewModel @AssistedInject constructor(
    @Assisted private val articleId: Int,
    private val devToApi: DevToApi,
    private val articleDao: ArticleDao,
    private val logger: Logger
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(articleId: Int): ArticleViewModel
    }

    val articleDetailUiModel: LiveData<ArticleDetailUiModel> = liveData {
        val articleDetailEntity = articleDao.findArticleDetail(articleId)
        if (articleDetailEntity == null) {
            try {
                val articleDetail = devToApi.findArticle(articleId)
                articleDao.insertArticle(articleDetail)
                emit(ArticleDetailUiModel(articleDetail))
            } catch (e: Throwable) {
                logger.e(e)
            }
        } else {
            emit(ArticleDetailUiModel(articleDetailEntity))
        }
    }

    init {
        viewModelScope.launch {
            try {
                articleDao.insertArticle(devToApi.findArticle(articleId))
            } catch (e: Throwable) {
                logger.e(e)
            }
        }
    }
}
