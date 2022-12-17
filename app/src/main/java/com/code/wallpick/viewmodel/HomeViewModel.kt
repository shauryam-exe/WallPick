package com.code.wallpick.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.wallpick.data.State
import com.code.wallpick.data.WallpaperRepository
import com.code.wallpick.data.model.PhotoList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class HomeViewModel(private val repository: WallpaperRepository) : ViewModel() {

    val wallpapers: LiveData<PhotoList>
        get() = repository.wallpapers

    val apiState: LiveData<State>
        get() = repository.state

    fun initTrendingWallpapers() = viewModelScope.launch(Dispatchers.IO) {
        repository.getTrendingWallpapers((0..50).random())
        //Log.d("wall viewModel","${wallpapers.value!!.photos[0].url}")

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
                Log.i("Image Saving", "Image saved at $file")
                return true
            } catch (e: Exception) {
                Log.i("Image Saving", "Failed to save image.")
                return false
            }
        }

}