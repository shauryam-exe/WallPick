package com.code.wallpick.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.wallpick.data.State
import com.code.wallpick.data.WallpaperRepository
import com.code.wallpick.data.model.PhotoList
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class WallpaperActivityViewModel(private val repository: WallpaperRepository): ViewModel() {

    val wallpapers: LiveData<PhotoList>
        get() = repository.wallpapers

    val apiState: LiveData<State>
        get() = repository.state

    fun getWallpapers(wallpaperType: String, page: Int) = viewModelScope.launch {
        repository.getWallpapers(wallpaperType, page)
    }

    val filesDir = "/data/data/com.code.wallpick/files/"


    fun saveWallpaper(folder: String, bmp: Bitmap, name: String) {
        try {
            val time = System.currentTimeMillis()
            var file = File(filesDir, folder)
            if (!file.exists()) {
                file.mkdir()
            }
            file = File(file, "$name.jpg")
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()

            Log.d("Thread",Thread.currentThread().name)
            Log.i("Image Saving", "Image saved at $file")
        } catch (e: Exception) {
            Log.i("Image Saving", "Failed to save image.")
        }
    }

}