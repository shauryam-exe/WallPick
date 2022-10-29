package com.code.wallpick.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

class AuthRepositoryImpl(private val firebaseAuth: FirebaseAuth) : AuthRepository {
    override val currentUser: FirebaseUser? = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): AuthState<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email,password).await()
            AuthState.Success(result.user!!)
        } catch (e: Exception) {
            AuthState.Failure(e)
        }
    }

    override suspend fun signup(
        email: String,
        password: String,
        name: String
    ): AuthState<FirebaseUser> {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }
}

suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            if (it.exception != null) {
                cont.resumeWithException(it.exception!!)
            } else {
                cont.resume(it.result, null)
            }
        }
    }
}