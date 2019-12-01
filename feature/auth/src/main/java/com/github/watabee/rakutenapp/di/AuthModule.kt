package com.github.watabee.rakutenapp.di

import com.github.watabee.rakutenapp.auth.AuthRepository
import com.github.watabee.rakutenapp.auth.AuthRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AuthModule {
    @Binds
    internal abstract fun bindAuthRepository(instance: AuthRepositoryImpl): AuthRepository
}
