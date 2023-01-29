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
    private val SENSOR_STOP_TIME = 30000L  // 30 seconds
    //lateinit var sensor: AccelerometerSensor

    companion object {
        var isSensorRunning = false
    }

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action.equals(Intent.ACTION_USER_PRESENT)) {
            Log.d(TAG,"Screen On Received")

            isSensorRunning = true

            val sharedPrefs = context.getSharedPreferences(App.PREFERENCES, Context.MODE_PRIVATE)
            val playlist = sharedPrefs.getString(App.HOME_PLAYLIST, App.FAVOURITE)!!

            sensor.startListening()
            sensor.setOnSensorValuesChangedListener { values ->
                sensor.detectShake(values, playlist)
            }

        }

        if (intent.action.equals(Intent.ACTION_SCREEN_OFF)) {
            sensor.stopListening()
            Log.d(TAG,"Screen Lock Received")
        }
    }

    fun handler() {
        val handler = Handler()
        handler.postDelayed({
            isSensorRunning = false

            if (sensor.isSensorListening)
                sensor.stopListening()
            else
                sensor.startListening()

//            Toast.makeText(
//                context,
//                "Sensor Stopped Finally",
//                Toast.LENGTH_SHORT
//            ).show()
            Log.d("Sensor","Sensor stopped")
        }, SENSOR_STOP_TIME)

    }

    fun stopSensor() {
        sensor.stopListening()
    }


}