package com.github.watabee.devtoapp.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.github.watabee.devtoapp.data.db.entities.ArticleDetailEntity

private const val MAX_CACHE_ARTICLE = 50

@Dao
abstract class ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    internal abstract suspend fun insert(articleDetailEntity: ArticleDetailEntity)

    @Transaction
    open suspend fun insertArticle(articleDetailEntity: ArticleDetailEntity) {
        val articleDetail = findArticleDetail(articleDetailEntity.id)
        if (articleDetail == null) {
            insert(articleDetailEntity)
            if (countArticles() > MAX_CACHE_ARTICLE) {
                deleteOldArticle()
            }
        }
    }

    @Query("SELECT * FROM article_details WHERE id = :id")
    abstract suspend fun findArticleDetail(id: Int): ArticleDetailEntity?

    @Query("SELECT COUNT(*) FROM article_details")
    internal abstract suspend fun countArticles(): Int

    @Query("DELETE FROM article_details WHERE id IN (SELECT id FROM article_details ORDER BY id LIMIT 1)")
    internal abstract suspend fun deleteOldArticle()
}
