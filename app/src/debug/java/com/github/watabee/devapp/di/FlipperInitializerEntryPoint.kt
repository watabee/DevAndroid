package com.github.watabee.devapp.di

import android.content.Context
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent

@EntryPoint
@InstallIn(ApplicationComponent::class)
interface FlipperInitializerEntryPoint {

    fun networkFlipperPlugin(): NetworkFlipperPlugin

    companion object {
        fun resolve(context: Context): FlipperInitializerEntryPoint =
            EntryPointAccessors.fromApplication(context, FlipperInitializerEntryPoint::class.java)
    }
}
