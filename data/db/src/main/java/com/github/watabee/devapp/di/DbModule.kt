package com.github.watabee.devapp.di

import android.content.Context
import com.github.watabee.devapp.data.db.AppDatabase
import com.github.watabee.devapp.data.db.daos.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideArticleDao(@ApplicationContext context: Context): ArticleDao =
        AppDatabase.getInstance(context).articleDao()
}
