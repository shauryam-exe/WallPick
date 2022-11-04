package com.code.wallpick.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.code.wallpick.api.WallpapersService
import com.code.wallpick.data.model.PhotoList

class WallpaperRepository(private val wallpapersService: WallpapersService) {

    private val wallpaperLiveData = MutableLiveData<PhotoList>()

    val wallpapers: LiveData<PhotoList>
    get() = wallpaperLiveData

    suspend fun getWallpapers(wallpaperType: String) {
        Log.d("pixel","getWallpaper Called")
//        val result = wallpapersService.getWallpaperList(wallpaperType)
        val result = wallpapersService.getWallpaperList("flower","portrait")
        Log.d("pixel","${result.body()!!.photos[2].url}")
//        if (result.body() != null) {
//            wallpaperLiveData.postValue(result.body())
//        }
    }

    suspend fun getTrendingWallpapers() {
        val result = wallpapersService.getCurated()
        if (result.body() != null) {
            wallpaperLiveData.postValue(result.body())
        }
    }

}