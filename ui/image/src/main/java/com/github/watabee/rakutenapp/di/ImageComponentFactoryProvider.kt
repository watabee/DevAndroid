package com.github.watabee.rakutenapp.di

interface ImageComponentFactoryProvider {
    fun glideComponentFactory(): ImageComponent.Factory
}
