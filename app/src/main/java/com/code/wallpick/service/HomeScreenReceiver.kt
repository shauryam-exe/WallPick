package com.code.wallpick.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.code.wallpick.service.sensor.AccelerometerSensor
import com.todo.shakeit.core.ShakeDetector
import com.todo.shakeit.core.ShakeListener


class HomeScreenReceiver : BroadcastReceiver() {
    private val TAG = "ScreenLockReceiver"
    private val SENSOR_STOP_TIME = 120000L  // 120 seconds

    companion object {
        var isSensorRunning = false
    }

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action.equals(Intent.ACTION_USER_PRESENT) && !isSensorRunning) {

            isSensorRunning = true

            val sensor = AccelerometerSensor(context)
            sensor.startListening()
            sensor.setOnSensorValuesChangedListener { values ->
                sensor.detectShake(values,"Favs")
            }

            val handler = Handler()
            handler.postDelayed({
                isSensorRunning = false

                if (sensor.isSensorListening)
                    sensor.stopListening()
                else
                    sensor.startListening()

                Toast.makeText(
                    context,
                    "Sensor Stopped Finally",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("Sensor","Sensor stopped")
            }, SENSOR_STOP_TIME)

            Log.d(TAG,"Screen On Received")
        }
    }



}