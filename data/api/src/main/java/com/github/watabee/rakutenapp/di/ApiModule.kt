package com.github.watabee.rakutenapp.di

import com.github.watabee.rakutenapp.data.api.IchibaItemApi
import com.github.watabee.rakutenapp.di.internal.NetworkModule
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
