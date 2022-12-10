package com.code.wallpick.data

sealed class ApiState {
    object Success: ApiState()
    object Loading: ApiState()
    object Failure: ApiState()
}