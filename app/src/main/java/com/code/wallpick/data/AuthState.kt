package com.code.wallpick.data

import java.lang.Exception

sealed class AuthState<out R> {
    data class Success<out R>(val result: R): AuthState<R>()
    data class Failure(val exception: Exception): AuthState<Nothing>()
    object Loading: AuthState<Nothing>()
}
