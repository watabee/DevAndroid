package com.github.watabee.rakutenapp.di

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.github.watabee.rakutenapp.appinitializers.AppInitializer
import com.github.watabee.rakutenapp.appinitializers.FlipperInitializer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Singleton
import okhttp3.Interceptor

@Module(includes = [DebugNetworkModule::class, DebugAppModule.Provider::class])
abstract class DebugAppModule {

    @Binds
    @IntoSet
    abstract fun bindFlipperInitializer(flipperInitializer: FlipperInitializer): AppInitializer

    @Module
    object Provider {
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
}
