package com.github.watabee.devtoapp.di

import com.github.watabee.devtoapp.auth.AuthRepository
import com.github.watabee.devtoapp.auth.AuthRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AuthModule {
    @Binds
    internal abstract fun bindAuthRepository(instance: AuthRepositoryImpl): AuthRepository
}
