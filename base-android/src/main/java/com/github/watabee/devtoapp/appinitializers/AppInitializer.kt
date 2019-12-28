package com.github.watabee.devtoapp.appinitializers

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}
