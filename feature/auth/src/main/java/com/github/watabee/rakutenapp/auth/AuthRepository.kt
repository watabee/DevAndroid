package com.github.watabee.rakutenapp.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

interface AuthRepository {
    fun isSignedIn(): Boolean

    fun isSignedInAsFlow(): Flow<Boolean>
}

internal class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    private val auth by lazy(LazyThreadSafetyMode.NONE) { FirebaseAuth.getInstance() }

    override fun isSignedIn(): Boolean = auth.currentUser != null

    @UseExperimental(ExperimentalCoroutinesApi::class)
    override fun isSignedInAsFlow(): Flow<Boolean> = channelFlow {
        val listener = FirebaseAuth.AuthStateListener {
            offer(it.currentUser != null)
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }
}
