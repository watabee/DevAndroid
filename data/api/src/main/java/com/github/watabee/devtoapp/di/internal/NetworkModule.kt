package com.github.watabee.devtoapp.di.internal

import android.content.Context
import com.github.watabee.devtoapp.di.Api
import com.github.watabee.devtoapp.di.Image
import com.github.watabee.devtoapp.di.InterceptorForApi
import com.github.watabee.devtoapp.di.InterceptorForImage
import com.github.watabee.devtoapp.di.NetworkInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
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
import java.util.Date

private const val CONNECT_TIMEOUT_SECONDS = 10L
private const val READ_TIMEOUT_SECONDS = 10L
private const val CACHE_SIZE = 30 * 1024 * 1024L

@Module
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
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
            .cache(Cache(File(appContext.cacheDir, "api"), CACHE_SIZE))
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
    @ElementsIntoSet
    fun provideEmptyInterceptorForApi(): Set<Interceptor> = emptySet()

    @InterceptorForImage
    @Provides
    @ElementsIntoSet
    fun provideEmptyInterceptorForImage(): Set<Interceptor> = emptySet()

    @NetworkInterceptor
    @Provides
    @ElementsIntoSet
    fun provideEmptyNetworkInterceptors(): Set<Interceptor> = emptySet()
}
