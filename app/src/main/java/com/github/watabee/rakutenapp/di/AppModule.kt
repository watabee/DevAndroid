package com.github.watabee.rakutenapp.di

import com.github.watabee.rakutenapp.appinitializers.AppInitializer
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.multibindings.ElementsIntoSet

@Module(
    includes = [
        AndroidInjectionModule::class,
        BaseModule::class,
        ApiModule::class,
        AuthModule::class,
        UiCommonModule::class,
        UiTopModule::class,
        UiRankingModule::class,
        UiSignInModule::class
    ]
)
object AppModule {

    // TODO: Delete if providing new app initializer
    @Provides
    @ElementsIntoSet
    fun provideEmptyAppInitializers(): Set<AppInitializer> = emptySet()
}
