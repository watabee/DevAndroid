package com.github.watabee.devapp.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.github.watabee.devapp.data.Article
import com.github.watabee.devapp.data.api.DevApi
import com.github.watabee.devapp.data.db.daos.ArticleDao
import com.github.watabee.devapp.util.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

internal class ArticleViewModel @AssistedInject constructor(
    @Assisted private val article: Article,
    private val devApi: DevApi,
    private val articleDao: ArticleDao,
    private val logger: Logger
) : ViewModel() {

    val articleDetailUiModel: LiveData<ArticleDetailUiModel> = liveData {
        emit(ArticleDetailUiModel(article))

        val articleDetailEntity = articleDao.findArticleDetail(article.id)
        if (articleDetailEntity == null) {
            try {
                val articleDetail = devApi.findArticle(article.id)
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

@AssistedFactory
internal interface ArticleViewModelFactory {
    fun create(article: Article): ArticleViewModel
}
