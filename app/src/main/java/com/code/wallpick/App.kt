package com.code.wallpick

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    companion object {
        const val SHAKE_SERVICE = "shakeService"

        const val PREFERENCES = "settings"
        const val HOME_PLAYLIST = "homePlaylist"
        const val LOCK_PLAYLIST = "lockPlaylist"
        const val FAVOURITE = "Favs"

    }
}