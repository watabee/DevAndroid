package com.github.watabee.devapp.di

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DebugAppModule {

    @Singleton
    @Provides
    fun provideNetworkFlipperPlugin(): NetworkFlipperPlugin {
        return NetworkFlipperPlugin()
    }

    @NetworkInterceptor
    @Provides
    @IntoSet
    fun provideFlipperOkHttpInterceptor(networkFlipperPlugin: NetworkFlipperPlugin): Interceptor {
        return FlipperOkhttpInterceptor(networkFlipperPlugin)
    }
}
