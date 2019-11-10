package com.github.watabee.rakutenapp.di.internal

import android.content.Context
import com.github.watabee.rakutenapp.data.api.interceptor.RakutenApiInterceptor
import com.github.watabee.rakutenapp.util.Logger
import com.squareup.moshi.Moshi
import dagger.Lazy
import dagger.Module
import dagger.Provides
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.Cache
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
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

    @Named("Api")
    @Provides
    @Singleton
    fun provideOkHttpClientForApi(okHttpClient: OkHttpClient, appContext: Context, logger: Logger): OkHttpClient =
        okHttpClient.newBuilder()
            .cache(Cache(File(appContext.cacheDir, "api"), 30 * 1024 * 1024L))
            .addInterceptor(RakutenApiInterceptor())
            .addInterceptor(HttpLoggingInterceptor(logger = createLogger(logger)).apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Named("Image")
    @Provides
    @Singleton
    fun provideOkHttpClientForImage(okHttpClient: OkHttpClient, logger: Logger): OkHttpClient =
        okHttpClient.newBuilder()
            .addInterceptor(HttpLoggingInterceptor(logger = createLogger(logger)).apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(@Named("Api") okHttpClient: Lazy<OkHttpClient>, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .callFactory(object : Call.Factory {
                override fun newCall(request: Request): Call = okHttpClient.get().newCall(request)
            })
            .baseUrl("https://app.rakuten.co.jp/services/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    private fun createLogger(logger: Logger) = object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            logger.w(message)
        }
    }
}
