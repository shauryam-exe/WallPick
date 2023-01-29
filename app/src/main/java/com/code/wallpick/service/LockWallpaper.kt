package com.code.wallpick.service

import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import com.code.wallpick.App
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.concurrent.locks.Lock

class LockWallpaper {

    companion object {
        private var i = 1
    }

    fun changeWallpaper(context: Context, playlist: String = "Favs") {
        val path = "${App.PATH}$playlist/"

        val files = File(path).listFiles()

        val child: String

        for (s in files) {
            Log.d("Reads",s.path)
        }

        if (LockWallpaper.i < files.size) {
            child = files!![LockWallpaper.i++].path
        } else {
            LockWallpaper.i = 0
            child = files!![LockWallpaper.i++].path
        }

        try {
            val file = File(child)
            val bitmap = BitmapFactory.decodeStream(FileInputStream(file))
            Log.d("Read",child)

            val wallpaperManager = WallpaperManager.getInstance(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK   )
            } else {
                wallpaperManager.setBitmap(bitmap)
            }

            Log.d("Read", "File Found")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

    }
}