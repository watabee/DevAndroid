package com.github.watabee.devtoapp.di

import com.github.watabee.devtoapp.ui.image.GlideModule
import dagger.Subcomponent

@Suppress("UnnecessaryAbstractClass")
@Subcomponent
abstract class ImageComponent {

    internal abstract fun inject(module: GlideModule)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ImageComponent
    }
}
