package com.github.watabee.devtoapp.di

interface ImageComponentFactoryProvider {
    fun glideComponentFactory(): ImageComponent.Factory
}
