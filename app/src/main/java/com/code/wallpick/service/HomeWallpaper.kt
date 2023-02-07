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


class HomeWallpaper {

    companion object {
        private var i = 1
    }

    fun changeWallpaper(context: Context, playlist: String = "Favs") {
        val path = "${App.PATH}$playlist/"

        val files = File(path).listFiles()

        val child: String


        try {
            if (files == null) return

            if (i < files.size) {
                child = files[i++].path
            } else {
                i = 0
                child = files[i++].path
            }

            val file = File(child)
            val bitmap = BitmapFactory.decodeStream(FileInputStream(file))

            val wallpaperManager = WallpaperManager.getInstance(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
            } else {
                wallpaperManager.setBitmap(bitmap)
            }

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

    }

}