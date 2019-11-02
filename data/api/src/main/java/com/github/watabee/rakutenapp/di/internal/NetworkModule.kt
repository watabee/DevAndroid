package com.github.watabee.rakutenapp.di.internal

import android.content.Context
import com.github.watabee.rakutenapp.data.api.interceptor.RakutenApiInterceptor
import com.github.watabee.rakutenapp.util.Logger
import com.github.watabee.rakutenapp.util.SchedulerProvider
import com.squareup.moshi.Moshi
import dagger.Lazy
import dagger.Module
import dagger.Provides
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Cache
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    fun provideCache(context: Context): Cache {
        val cacheSize = 30 * 1024 * 1024L
        val file = File(context.cacheDir, "RakutenApp")
        return Cache(file, cacheSize)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache, logger: Logger): OkHttpClient =
        OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(10L, TimeUnit.SECONDS)
            .addInterceptor(RakutenApiInterceptor())
            .addInterceptor(HttpLoggingInterceptor(logger = createLogger(logger)).apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: Lazy<OkHttpClient>,
        moshi: Moshi,
        schedulerProvider: SchedulerProvider
    ): Retrofit =
        Retrofit.Builder()
            .callFactory(object : Call.Factory {
                override fun newCall(request: Request): Call = okHttpClient.get().newCall(request)
            })
            .baseUrl("https://app.rakuten.co.jp/services/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.createWithScheduler(schedulerProvider.io)
            )
            .build()

    private fun createLogger(logger: Logger) = object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            logger.w(message)
        }
    }
}
