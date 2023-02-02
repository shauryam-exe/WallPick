package com.code.wallpick.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.code.wallpick.App
import com.code.wallpick.service.sensor.AccelerometerSensor
import com.code.wallpick.service.sensor.AndroidSensor


class HomeScreenReceiver(val sensor: AccelerometerSensor) : BroadcastReceiver() {
    private val TAG = "ScreenLockReceiver"

    private var playlist = App.FAVOURITE

    companion object {
        var playlistChanged = false
    }

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action.equals(Intent.ACTION_USER_PRESENT)) {

            val sharedPrefs = context.getSharedPreferences(App.PREFERENCES, Context.MODE_PRIVATE)
            playlist = sharedPrefs.getString(App.HOME_PLAYLIST, App.FAVOURITE)!!

            sensor.startListening()
            sensor.setOnSensorValuesChangedListener { values ->
                if(sensor.detectShake(values, playlist)) {
                    playlist = sharedPrefs.getString(App.HOME_PLAYLIST, App.FAVOURITE)!!
                }
                if (playlistChanged) {
                    playlist = sharedPrefs.getString(App.HOME_PLAYLIST, App.FAVOURITE)!!
                    playlistChanged = false
                }
            }

        }

        if (intent.action.equals(Intent.ACTION_SCREEN_OFF)) {
            sensor.stopListening()
        }
    }

    fun stopSensor() {
        sensor.stopListening()
    }


}