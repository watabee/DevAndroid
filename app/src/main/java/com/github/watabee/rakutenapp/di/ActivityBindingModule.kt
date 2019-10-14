package com.github.watabee.rakutenapp.di

import com.github.watabee.rakutenapp.MainActivityBuilder
import dagger.Module

@Module(
    includes = [
        MainActivityBuilder::class
    ]
)
object ActivityBindingModule