package com.code.wallpick.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.code.wallpick.api.WallpapersService
import com.code.wallpick.data.model.PhotoList
import com.google.android.gms.common.api.Api

class WallpaperRepository(private val wallpapersService: WallpapersService) {

    private val wallpaperLiveData = MutableLiveData<PhotoList>()

    val wallpapers: LiveData<PhotoList>
    get() = wallpaperLiveData

    private val apiStateLiveData = MutableLiveData<ApiState>()

    val apiState: LiveData<ApiState>
    get() = apiStateLiveData

    suspend fun getWallpapers(wallpaperType: String) {

    }

    suspend fun getTrendingWallpapers(page: Int) {
        apiStateLiveData.postValue(ApiState.Loading)
        val result = wallpapersService.getCurated(page,41)
        if (result.body() != null) {
            wallpaperLiveData.postValue(result.body())
            apiStateLiveData.postValue(ApiState.Success)
        }
    }

}