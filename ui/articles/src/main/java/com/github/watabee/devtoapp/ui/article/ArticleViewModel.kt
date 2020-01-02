package com.github.watabee.devtoapp.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.github.watabee.devtoapp.data.api.DevToApi
import com.github.watabee.devtoapp.data.api.response.ArticleDetail
import com.github.watabee.devtoapp.data.db.daos.ArticleDao
import com.github.watabee.devtoapp.data.db.entities.ArticleDetailEntity
import com.github.watabee.devtoapp.data.db.entities.ArticleUserEntity
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
                val entity = devToApi.findArticle(articleId).toEntity()
                articleDao.insertArticle(entity)
                emit(ArticleDetailUiModel(entity))
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
                articleDao.insertArticle(devToApi.findArticle(articleId).toEntity())
            } catch (e: Throwable) {
                logger.e(e)
            }
        }
    }
}

private fun ArticleDetail.toEntity(): ArticleDetailEntity =
    ArticleDetailEntity(
        id = id, typeOf = typeOf, title = title, description = description, coverImage = coverImage,
        readablePublishDate = readablePublishDate, tags = tags, url = url, bodyMarkdown = bodyMarkdown, commentsCount = commentsCount,
        positiveReactionsCount = positiveReactionsCount, publishedAt = publishedAt,
        user = ArticleUserEntity(
            name = user.name, username = user.username, profileImage = user.profileImage, profileImage90 = user.profileImage90
        )
    )
