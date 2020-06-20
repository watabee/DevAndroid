package com.github.watabee.devtoapp.di

import android.content.Context
import com.github.watabee.devtoapp.data.db.AppDatabase
import com.github.watabee.devtoapp.data.db.daos.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideArticleDao(@ApplicationContext context: Context): ArticleDao =
        AppDatabase.getInstance(context).articleDao()
}
