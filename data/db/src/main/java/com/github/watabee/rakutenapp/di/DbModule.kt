package com.github.watabee.rakutenapp.di

import android.content.Context
import com.github.watabee.rakutenapp.data.db.AppDatabase
import com.github.watabee.rakutenapp.data.db.daos.FavoriteIchibaItemDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DbModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(context: Context): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideFavoriteIchibaItemDao(db: AppDatabase): FavoriteIchibaItemDao = db.favoriteIchibaItemDao()
}
