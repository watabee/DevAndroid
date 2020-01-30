package com.github.watabee.devtoapp.di

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.github.watabee.devtoapp.extensions.getColor
import com.github.watabee.devtoapp.ui.common.R
import com.github.watabee.devtoapp.util.AppFragmentFactory
import com.github.watabee.devtoapp.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonConfiguration
import javax.inject.Singleton

@Module
abstract class UiCommonModule {

    @Binds
    @Singleton
    internal abstract fun bindFragmentFactory(instance: AppFragmentFactory): FragmentFactory

    @Binds
    @Singleton
    internal abstract fun bindViewModelFactory(instance: ViewModelFactory): ViewModelProvider.Factory

    companion object {
        @Provides
        @Singleton
        internal fun provideMarkwon(context: Context): Markwon =
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
}
