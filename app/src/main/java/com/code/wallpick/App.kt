package com.code.wallpick

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    companion object {
        const val PLAYLIST = "activePlaylist"
        const val HOME_PLAYLIST = "homePlaylist"
        const val LOCK_PLAYLIST = "lockPlaylist"
        const val FAVOURITE = "Favs"

    }
}