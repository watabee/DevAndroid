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
    fun provideFavoriteIchibaItemDao(context: Context): FavoriteIchibaItemDao = AppDatabase.getInstance(context).favoriteIchibaItemDao()
}
