package com.github.watabee.devapp.di

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@EntryPoint
@InstallIn(SingletonComponent::class)
internal interface GlideEntryPoint {

    @Image
    fun okHttpClient(): OkHttpClient

    companion object {
        fun resolve(context: Context): GlideEntryPoint =
            EntryPointAccessors.fromApplication(context, GlideEntryPoint::class.java)
    }
}
