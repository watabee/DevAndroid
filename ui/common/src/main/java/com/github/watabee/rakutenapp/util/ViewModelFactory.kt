package com.github.watabee.rakutenapp.util

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import javax.inject.Provider

class ViewModelFactory @AssistedInject constructor(
    @Assisted owner: SavedStateRegistryOwner,
    @Assisted defaultArgs: Bundle?,
    private val factories: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<AppViewModelFactory<out ViewModel>>>
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @AssistedInject.Factory
    interface Factory {
        fun create(owner: SavedStateRegistryOwner, defaultArgs: Bundle?): ViewModelFactory
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return factories[modelClass]?.get()?.create(handle) as? T
            ?: throw IllegalArgumentException("Unknown model class $modelClass")
    }
}
