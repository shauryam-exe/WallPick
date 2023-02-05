package com.code.wallpick

import android.annotation.SuppressLint
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    companion object {
        const val SHAKE_SERVICE = "shakeService"

        const val PREFERENCES = "settings"
        const val HOME_PLAYLIST = "homePlaylist"
        const val LOCK_PLAYLIST = "lockPlaylist"
        const val FAVOURITE = "Favourites"

        @SuppressLint("SdCardPath")
        const val PATH = "/data/data/com.code.wallpick/files/saved/"
    }
}