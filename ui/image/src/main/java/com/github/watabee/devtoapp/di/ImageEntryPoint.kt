package com.github.watabee.devtoapp.di

import android.content.Context
import com.github.watabee.devtoapp.ui.image.GlideModule
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent

@EntryPoint
@InstallIn(ApplicationComponent::class)
internal interface ImageEntryPoint {

    fun inject(glideModule: GlideModule)

    companion object {
        fun resolve(context: Context): ImageEntryPoint =
            EntryPointAccessors.fromApplication(context, ImageEntryPoint::class.java)
    }
}
