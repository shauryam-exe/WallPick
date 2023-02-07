package com.code.wallpick.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.code.wallpick.App
import com.code.wallpick.service.sensor.AccelerometerSensor


class ShakeService : Service() {

    private val sensor = AccelerometerSensor(this)
    private val homeScreenReceiver = HomeScreenReceiver(sensor)
    private val lockScreenReceiver = LockScreenReceiver()

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= 26) {
            val CHANNEL_ID = "my_channel_01"
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                channel
            )
            val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("")
                .setContentText("").build()
            startForeground(1, notification)
        }

//      LocalBroadcastManager.getInstance(this).registerReceiver(HomeScreenReceiver(), IntentFilter(Intent.ACTION_USER_PRESENT))
        detectingShake()
        initHomeReceiver()

        initLockReceiver()


        Log.d("Service","Service Started!")
    }

    private fun initLockReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_SCREEN_ON)
        registerReceiver(lockScreenReceiver,intentFilter)
    }

    private fun initHomeReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_USER_PRESENT)
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
        registerReceiver(homeScreenReceiver,intentFilter)
    }

    private fun detectingShake() {
        val sharedPrefs = getSharedPreferences(App.PREFERENCES, Context.MODE_PRIVATE)
        val playlist = sharedPrefs.getString(App.HOME_PLAYLIST, App.FAVOURITE)!!
        sensor.startListening()
        sensor.setOnSensorValuesChangedListener { values ->
            sensor.detectShake(values, playlist)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // return the START_STICKY flag to indicate that the Service should continue running until it is explicitly stopped
        return START_STICKY
    }

    override fun onDestroy() {
        Log.d("Service","On Destroy Called")
        val sharedPrefs = getSharedPreferences(App.PREFERENCES, MODE_PRIVATE)

        if (sharedPrefs.getBoolean(App.SHAKE_SERVICE,false)) {
            val restart = Intent(applicationContext, this.javaClass);
            restart.setPackage(packageName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(restart)
            } else {
                startService(restart)
            }
        } else {
            Log.d("Service","Service Destroyed")
            val edit = sharedPrefs.edit()
            edit.putBoolean(App.SHAKE_SERVICE,false)
            edit.apply()
            unregisterReceiver(lockScreenReceiver)
            unregisterReceiver(homeScreenReceiver)
            homeScreenReceiver.stopSensor()
        }
        super.onDestroy()
    }

    override fun stopService(name: Intent?): Boolean {
        return super.stopService(name)
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }


}