package com.github.watabee.rakutenapp.di.internal

import android.content.Context
import com.github.watabee.rakutenapp.data.api.interceptor.RakutenApiInterceptor
import com.github.watabee.rakutenapp.di.Api
import com.github.watabee.rakutenapp.di.Image
import com.github.watabee.rakutenapp.di.InterceptorForApi
import com.github.watabee.rakutenapp.di.InterceptorForImage
import com.github.watabee.rakutenapp.di.NetworkInterceptor
import com.squareup.moshi.Moshi
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Cache
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(10L, TimeUnit.SECONDS)
            .build()

    @Api
    @Provides
    @Singleton
    fun provideOkHttpClientForApi(
        okHttpClient: OkHttpClient,
        appContext: Context,
        @InterceptorForApi interceptors: Set<@JvmSuppressWildcards Interceptor>,
        @NetworkInterceptor networkInterceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient =
        okHttpClient.newBuilder()
            .cache(Cache(File(appContext.cacheDir, "api"), 30 * 1024 * 1024L))
            .apply {
                interceptors.forEach { addInterceptor(it) }
                networkInterceptors.forEach { addNetworkInterceptor(it) }
            }
            .build()

    @Image
    @Provides
    @Singleton
    fun provideOkHttpClientForImage(
        okHttpClient: OkHttpClient,
        @InterceptorForImage interceptors: Set<@JvmSuppressWildcards Interceptor>,
        @NetworkInterceptor networkInterceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient =
        okHttpClient.newBuilder()
            .apply {
                interceptors.forEach { addInterceptor(it) }
                networkInterceptors.forEach { addNetworkInterceptor(it) }
            }
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(@Api okHttpClient: Lazy<OkHttpClient>, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .callFactory(object : Call.Factory {
                override fun newCall(request: Request): Call = okHttpClient.get().newCall(request)
            })
            .baseUrl("https://app.rakuten.co.jp/services/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @InterceptorForApi
    @Provides
    @IntoSet
    fun provideRakutenApiInterceptor(): Interceptor = RakutenApiInterceptor()

    @InterceptorForImage
    @Provides
    @ElementsIntoSet
    fun provideEmptyInterceptorForImage(): Set<Interceptor> = emptySet()

    @NetworkInterceptor
    @Provides
    @ElementsIntoSet
    fun provideEmptyNetworkInterceptors(): Set<Interceptor> = emptySet()
}
