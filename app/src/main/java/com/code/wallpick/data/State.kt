package com.code.wallpick.data

sealed class State {
    object Success: State()
    object Loading: State()
    object Failure: State()
}