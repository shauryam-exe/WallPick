package com.code.wallpick.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat.startForegroundService
import com.code.wallpick.App

class BootCompleteReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val sharedPrefs = context.getSharedPreferences(App.PREFERENCES,Context.MODE_PRIVATE)
        val serviceRunning = sharedPrefs.getBoolean(App.SHAKE_SERVICE,false)

        Log.d("Boot Completed","Booted: $serviceRunning")
        if (intent.action.equals(Intent.ACTION_BOOT_COMPLETED) && serviceRunning) {
            registerService(context)
        }
    }

    private fun registerService(context: Context) {
        val serviceIntent = Intent(context, ShakeService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }
}