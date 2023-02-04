package com.code.wallpick.data.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(credential: AuthCredential): AuthState<FirebaseUser>
    suspend fun signup(email: String, password: String, name: String): AuthState<FirebaseUser>
    suspend fun logout()
    suspend fun loginAnonymous(): AuthState<FirebaseUser>?
}