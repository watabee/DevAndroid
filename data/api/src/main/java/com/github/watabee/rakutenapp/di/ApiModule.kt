package com.github.watabee.rakutenapp.di

import com.github.watabee.rakutenapp.data.api.IchibaItemApi
import com.github.watabee.rakutenapp.di.internal.NetworkModule
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
object ApiModule {

    @JvmStatic
    @Provides
    @Singleton
    internal fun provideIchibaItemApi(retrofit: Retrofit): IchibaItemApi =
        retrofit.create(IchibaItemApi::class.java)
}