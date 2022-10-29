package com.code.wallpick.data

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): AuthState<FirebaseUser>
    suspend fun signup(email: String, password: String, name: String): AuthState<FirebaseUser>
    suspend fun logout()
}