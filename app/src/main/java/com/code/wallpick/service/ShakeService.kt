package com.code.wallpick.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.todo.shakeit.core.ShakeDetector
import com.todo.shakeit.core.ShakeListener


class ShakeService : Service() {
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

        registerReceiver(HomeScreenReceiver(), IntentFilter(Intent.ACTION_USER_PRESENT))

        Log.d("Service","Service Started!")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // return the START_STICKY flag to indicate that the Service should continue running until it is explicitly stopped
        return START_STICKY
    }

    override fun onDestroy() {
        val  restart=Intent(applicationContext,this.javaClass);
        restart.setPackage(packageName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(restart)
        } else {
            startService(restart)
        }
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }


}