package com.github.watabee.devtoapp.di

import com.github.watabee.devtoapp.data.api.DevToApi
import com.github.watabee.devtoapp.di.internal.NetworkModule
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
object ApiModule {

    @Provides
    @Singleton
    internal fun provideDevToApi(retrofit: Retrofit): DevToApi = retrofit.create(DevToApi::class.java)
}
