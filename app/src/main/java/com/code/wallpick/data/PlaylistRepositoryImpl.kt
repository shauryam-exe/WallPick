package com.code.wallpick.data

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.io.File
import java.io.FileOutputStream

class PlaylistRepositoryImpl : PlaylistRepository {


    @SuppressLint("SdCardPath")
    private val dir = "/data/data/com.code.wallpick/files/"


    override fun saveWallpaper(folder: String, bmp: Bitmap, name: String, filesDir: File) {
        try {
            //val time = System.currentTimeMillis()
            var file = File(filesDir, folder)
            if (!file.exists()) {
                file.mkdir()
            }
            file = File(file, "$name.jpg")
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()

            Log.d("Thread", Thread.currentThread().name)
            Log.i("Image Saving", "Image saved at $file")
        } catch (e: Exception) {
            Log.i("Image Saving", "Failed to save image.")
        }
    }

    override fun loadPlaylist(playlistName: String): Array<File> {
        val path = "$dir$playlistName/"
        val file = File(path)
        if (!file.exists()) {
            file.mkdir()
        }

        val playlist = file.listFiles()
        Log.d("Playlist",playlist[0].toString())
        Log.d("Playlist",playlistName)

        return playlist!!
    }

    override fun listOfPlaylists(): Array<File> {
        val path = dir
        val playlists = File(path).listFiles()
        return playlists!!
    }
}