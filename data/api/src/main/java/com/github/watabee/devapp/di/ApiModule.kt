package com.github.watabee.devapp.di

import com.github.watabee.devapp.data.api.DevApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    internal fun provideDevApi(retrofit: Retrofit): DevApi = retrofit.create(DevApi::class.java)
}
