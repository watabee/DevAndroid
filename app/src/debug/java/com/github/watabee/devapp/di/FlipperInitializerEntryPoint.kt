package com.github.watabee.devapp.di

import android.content.Context
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FlipperInitializerEntryPoint {

    fun networkFlipperPlugin(): NetworkFlipperPlugin

    companion object {
        fun resolve(context: Context): FlipperInitializerEntryPoint =
            EntryPointAccessors.fromApplication(context, FlipperInitializerEntryPoint::class.java)
    }
}
