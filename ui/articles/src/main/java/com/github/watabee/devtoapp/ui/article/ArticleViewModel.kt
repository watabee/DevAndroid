package com.github.watabee.devtoapp.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.github.watabee.devtoapp.data.Article
import com.github.watabee.devtoapp.data.api.DevToApi
import com.github.watabee.devtoapp.data.db.daos.ArticleDao
import com.github.watabee.devtoapp.util.Logger
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

internal class ArticleViewModel @AssistedInject constructor(
    @Assisted private val article: Article,
    private val devToApi: DevToApi,
    private val articleDao: ArticleDao,
    private val logger: Logger
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(article: Article): ArticleViewModel
    }

    val articleDetailUiModel: LiveData<ArticleDetailUiModel> = liveData {
        emit(ArticleDetailUiModel(article))

        val articleDetailEntity = articleDao.findArticleDetail(article.id)
        if (articleDetailEntity == null) {
            try {
                val articleDetail = devToApi.findArticle(article.id)
                articleDao.insertArticle(articleDetail)
                emit(ArticleDetailUiModel(articleDetail))
            } catch (e: Throwable) {
                logger.e(e)
            }
        } else {
            emit(ArticleDetailUiModel(articleDetailEntity))
        }
    }
}
