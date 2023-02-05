package com.code.wallpick.service.sensor

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import com.code.wallpick.service.HomeWallpaper
import kotlin.math.sqrt

class AccelerometerSensor(context: Context) :
    AndroidSensor(context, PackageManager.FEATURE_SENSOR_ACCELEROMETER, Sensor.TYPE_ACCELEROMETER) {
    private val SHAKE_THRESHOLD = 30

    fun detectShake(values: List<Float>,playlist: String): Boolean {
        Log.d("sensor","Detect Shake calling")
        val ax = values[0]
        val ay = values[1]
        val az = values[2]
        val acceleration = sqrt((ax * ax + ay * ay + az * az).toDouble())

        if (acceleration > SHAKE_THRESHOLD) {

            Log.d("check sensor", "Sensor is working perfectly fine!!!")


            HomeWallpaper().changeWallpaper(context,playlist)

            //Stopping and Restarting the timer
            this.stopListening()
            object : CountDownTimer(500, 1000) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    this@AccelerometerSensor.startListening()
                }
            }.start()
            return true
        }
        return false
    }

//    fun isAppRunning(context: Context): String? {
//        val appChecker = AppChecker()
//        return appChecker.getForegroundApp(context)
//    }
}