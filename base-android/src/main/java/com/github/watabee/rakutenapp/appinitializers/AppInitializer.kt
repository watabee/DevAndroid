package com.github.watabee.rakutenapp.appinitializers

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}
