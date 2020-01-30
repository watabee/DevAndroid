package com.github.watabee.devtoapp.di

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.github.watabee.devtoapp.appinitializers.AppInitializer
import com.github.watabee.devtoapp.appinitializers.FlipperInitializer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import javax.inject.Singleton

@Module(includes = [DebugNetworkModule::class])
abstract class DebugAppModule {

    @Binds
    @IntoSet
    abstract fun bindFlipperInitializer(flipperInitializer: FlipperInitializer): AppInitializer

    companion object {
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
