package com.github.watabee.devtoapp.util

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

interface AppViewModelFactory<T : ViewModel> {
    fun create(handle: SavedStateHandle): T
}
