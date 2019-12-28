package com.github.watabee.devtoapp.di

import com.github.watabee.devtoapp.data.api.IchibaItemApi
import com.github.watabee.devtoapp.di.internal.NetworkModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import retrofit2.Retrofit

@Module(includes = [NetworkModule::class])
object ApiModule {

    @Provides
    @Singleton
    internal fun provideIchibaItemApi(retrofit: Retrofit): IchibaItemApi =
        retrofit.create(IchibaItemApi::class.java)
}
