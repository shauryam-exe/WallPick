package com.code.wallpick.service

import android.R
import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException


class Util {

    companion object {
        private var i = 0
    }

    fun changeWallpaper(context: Context, playlist: String = "Favs") {
        val path = "/data/data/com.code.wallpick/files/$playlist/"

        val files = File(path).listFiles()

        val child: String

        for (s in files) {
            Log.d("Reads",s.path)
        }

        if (i < files.size) {
            child = files!![i++].path
        } else {
            i = 0
            child = files!![i++].path
        }

        try {
            //val child = "14559395.jpg"
            val file = File(child)
            val bitmap = BitmapFactory.decodeStream(FileInputStream(file))
            Log.d("Read",child)

            val wallpaperManager = WallpaperManager.getInstance(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
            } else {
                wallpaperManager.setBitmap(bitmap)
            }

            Log.d("Read", "File Found")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

    }

}