package com.code.wallpick.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.wallpick.data.remote.State
import com.code.wallpick.data.model.PhotoList
import com.code.wallpick.data.remote.WallpaperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (private val repository: WallpaperRepository) : ViewModel() {

    val wallpapers: LiveData<PhotoList>
        get() = repository.curatedWallpapers

    val apiState: LiveData<State>
        get() = repository.state

    fun initTrendingWallpapers() = viewModelScope.launch(Dispatchers.IO) {
        repository.getTrendingWallpapers((0..50).random())
    }

    fun loadTrendingWallpapers(page: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.getTrendingWallpapers(page)
    }

    fun saveImage(folder: String, bmp: Bitmap, name: String, filesDir: File): Boolean {
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
                Log.d("Image Saving", "Image saved at $file")
                return true
            } catch (e: Exception) {
                Log.i("Image Saving", "Failed to save image.")
                return false
            }
        }

}