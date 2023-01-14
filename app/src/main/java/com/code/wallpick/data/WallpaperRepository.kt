package com.code.wallpick.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.code.wallpick.api.WallpapersService
import com.code.wallpick.data.model.PhotoList

class WallpaperRepository(private val wallpapersService: WallpapersService) {

    private val curatedWallpaperLiveData = MutableLiveData<PhotoList>()

    val curatedWallpapers: LiveData<PhotoList>
    get() = curatedWallpaperLiveData


    private val wallpaperLiveData = MutableLiveData<PhotoList>()

    val wallpapers: LiveData<PhotoList>
        get() = wallpaperLiveData


    private val stateLiveData = MutableLiveData<State>()

    val state: LiveData<State>
        get() = stateLiveData

    suspend fun getWallpapers(wallpaperType: String, page: Int) {
        stateLiveData.postValue(State.Loading)
        val result = wallpapersService
            .getWallpaperList(wallpaperType,
                "portrait",page,15)
        if (result.body() != null) {
            wallpaperLiveData.postValue(result.body())
            stateLiveData.postValue(State.Success)
        }
    }

    suspend fun getTrendingWallpapers(page: Int) {
        stateLiveData.postValue(State.Loading)
        val result = wallpapersService.getCurated(page,41)
        if (result.body() != null) {
            curatedWallpaperLiveData.postValue(result.body())
            stateLiveData.postValue(State.Success)
        }
    }

}