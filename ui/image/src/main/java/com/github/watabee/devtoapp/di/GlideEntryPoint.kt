package com.github.watabee.devtoapp.di

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient

@EntryPoint
@InstallIn(ApplicationComponent::class)
internal interface GlideEntryPoint {

    @Image
    fun okHttpClient(): OkHttpClient

    companion object {
        fun resolve(context: Context): GlideEntryPoint =
            EntryPointAccessors.fromApplication(context, GlideEntryPoint::class.java)
    }
}
