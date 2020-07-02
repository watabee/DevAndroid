package com.github.watabee.devapp.di

import com.github.watabee.devapp.auth.AuthRepository
import com.github.watabee.devapp.auth.AuthRepositoryImpl
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
