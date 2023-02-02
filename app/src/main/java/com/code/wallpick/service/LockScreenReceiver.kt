package com.code.wallpick.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.code.wallpick.App

class LockScreenReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action.equals(Intent.ACTION_SCREEN_ON)) {

            val sharedPrefs = context.getSharedPreferences(App.PREFERENCES, Context.MODE_PRIVATE)
            val playlist = sharedPrefs.getString(App.LOCK_PLAYLIST, App.FAVOURITE)!!

            LockWallpaper().changeWallpaper(context,playlist)
        }
    }
}