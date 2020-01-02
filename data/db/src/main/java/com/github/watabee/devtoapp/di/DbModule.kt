package com.github.watabee.devtoapp.di

import android.content.Context
import com.github.watabee.devtoapp.data.db.AppDatabase
import com.github.watabee.devtoapp.data.db.daos.ArticleDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DbModule {

    @Provides
    @Singleton
    fun provideArticleDao(context: Context): ArticleDao =
        AppDatabase.getInstance(context).articleDao()
}
