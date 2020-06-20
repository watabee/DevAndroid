package com.github.watabee.devtoapp.di

import com.github.watabee.devtoapp.data.api.DevToApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApiModule {

    @Provides
    @Singleton
    internal fun provideDevToApi(retrofit: Retrofit): DevToApi = retrofit.create(DevToApi::class.java)
}
