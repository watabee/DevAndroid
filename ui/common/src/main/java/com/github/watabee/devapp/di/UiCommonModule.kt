package com.github.watabee.devapp.di

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.github.watabee.devapp.extensions.getColor
import com.github.watabee.devapp.ui.common.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonConfiguration
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object UiCommonModule {

    @Provides
    @Singleton
    internal fun provideMarkwon(@ApplicationContext context: Context): Markwon =
        Markwon.builder(context)
            .usePlugin(object : AbstractMarkwonPlugin() {
                override fun configureConfiguration(builder: MarkwonConfiguration.Builder) {
                    builder.linkResolver { view, link ->
                        val primaryColor = view.context.theme.getColor(R.attr.colorPrimary)
                        val customTabsIntent: CustomTabsIntent = CustomTabsIntent.Builder()
                            .setToolbarColor(primaryColor)
                            .setShowTitle(true)
                            .build()
                        customTabsIntent.launchUrl(view.context, Uri.parse(link))
                    }
                }
            })
            .build()
}
