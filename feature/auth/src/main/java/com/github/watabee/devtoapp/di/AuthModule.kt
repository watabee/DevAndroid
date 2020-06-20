package com.github.watabee.devtoapp.di

import com.github.watabee.devtoapp.auth.AuthRepository
import com.github.watabee.devtoapp.auth.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class AuthModule {
    @Binds
    internal abstract fun bindAuthRepository(instance: AuthRepositoryImpl): AuthRepository
}
