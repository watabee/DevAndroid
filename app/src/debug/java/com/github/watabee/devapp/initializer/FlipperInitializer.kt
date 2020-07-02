package com.github.watabee.devapp.initializer

import android.content.Context
import androidx.startup.Initializer
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
import com.github.watabee.devapp.di.FlipperInitializerEntryPoint

class FlipperInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        SoLoader.init(context, false)

        val networkFlipperPlugin = FlipperInitializerEntryPoint.resolve(context).networkFlipperPlugin()

        if (FlipperUtils.shouldEnableFlipper(context)) {
            val flipperClient = AndroidFlipperClient.getInstance(context)
            flipperClient.addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
            flipperClient.addPlugin(networkFlipperPlugin)
            flipperClient.start()
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(TimberInitializer::class.java)
}
