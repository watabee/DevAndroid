package com.github.watabee.devtoapp.ui.image

import android.app.ActivityManager
import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.github.watabee.devtoapp.di.Image
import com.github.watabee.devtoapp.di.ImageComponentFactoryProvider
import java.io.InputStream
import javax.inject.Inject
import okhttp3.OkHttpClient

private const val DISK_CACHE_SIZE = 50 * 1024 * 1024L

@Excludes(OkHttpLibraryGlideModule::class)
@GlideModule
internal class GlideModule : AppGlideModule() {

    @field:Image @Inject lateinit var okHttpClient: OkHttpClient

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val options = RequestOptions().format(if (am.isLowRamDevice) DecodeFormat.PREFER_RGB_565 else DecodeFormat.PREFER_ARGB_8888)

        builder.setDefaultRequestOptions(options)
            .setDiskCache(InternalCacheDiskCacheFactory(context, "image", DISK_CACHE_SIZE))
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(Log.VERBOSE)
        }
    }

    override fun isManifestParsingEnabled(): Boolean = false

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        // Application must implement GlideComponentFactoryProvider.
        (context.applicationContext as ImageComponentFactoryProvider).glideComponentFactory().create().inject(this)
        glide.registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient))
    }
}
