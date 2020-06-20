package com.github.watabee.devtoapp.di

import com.github.watabee.devtoapp.util.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(ApplicationComponent::class)
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
