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
    private val SENSOR_STOP_TIME = 30000L

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action.equals(Intent.ACTION_USER_PRESENT)) {

            val sensor = AccelerometerSensor(context)
            sensor.startListening()
            sensor.setOnSensorValuesChangedListener { values ->
                sensor.detectShake(values)
            }

            val handler = Handler()
            handler.postDelayed({
                sensor.stopListening()
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

    private fun detectShake(context: Context) {
        ShakeDetector.registerForShakeEvent(object: ShakeListener {
            override fun onShake() {
                Log.d("shake","shake detected")
                Toast.makeText(context,"Shake Detected",Toast.LENGTH_LONG).show()
            }
        })
    }


}