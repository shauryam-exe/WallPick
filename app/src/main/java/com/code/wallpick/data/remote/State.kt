package com.code.wallpick.data.remote

sealed class State {
    object Success: State()
    object Loading: State()
    object Failure: State()
}