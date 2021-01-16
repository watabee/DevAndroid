package com.github.watabee.devapp.di

import com.github.watabee.devapp.util.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object DebugNetworkModule {

    @InterceptorForApi
    @Provides
    @IntoSet
    fun provideHttpLoggingInterceptorForApi(logger: Logger): Interceptor {
        return HttpLoggingInterceptor(logger = createLogger(logger)).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @InterceptorForImage
    @Provides
    @IntoSet
    fun provideHttpLoggingInterceptorForImage(logger: Logger): Interceptor {
        return HttpLoggingInterceptor(logger = createLogger(logger)).apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }

    private fun createLogger(logger: Logger) = object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            logger.w(message)
        }
    }
}
