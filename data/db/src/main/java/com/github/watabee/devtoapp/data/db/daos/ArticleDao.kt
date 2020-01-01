package com.github.watabee.devtoapp.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.github.watabee.devtoapp.data.db.entities.ArticleDetailEntity

@Dao
abstract class ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    internal abstract suspend fun insert(articleDetailEntity: ArticleDetailEntity)

    @Transaction
    suspend fun insertArticle(articleDetailEntity: ArticleDetailEntity) {
        val articleDetail = findArticleDetail(articleDetailEntity.id)
        if (articleDetail == null || articleDetail.bodyMarkdown.isEmpty()) {
            insert(articleDetailEntity)
        }
    }

    @Query("SELECT * FROM article_details WHERE id = :id")
    abstract suspend fun findArticleDetail(id: Int): ArticleDetailEntity?
}