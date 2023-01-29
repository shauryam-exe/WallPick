package com.code.wallpick.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.code.wallpick.data.model.PhotoList

class WallpaperRepositoryImpl(private val wallpapersService: WallpapersService): WallpaperRepository {

    private val curatedWallpaperLiveData = MutableLiveData<PhotoList>()

    override val curatedWallpapers: LiveData<PhotoList>
    get() = curatedWallpaperLiveData


    private val wallpaperLiveData = MutableLiveData<PhotoList>()

    val wallpapers: LiveData<PhotoList>
        get() = wallpaperLiveData


    private val stateLiveData = MutableLiveData<State>()

    override val state: LiveData<State>
        get() = stateLiveData

    override suspend fun getWallpapers(wallpaperType: String, page: Int) {
        stateLiveData.postValue(State.Loading)
        val result = wallpapersService
            .getWallpaperList(wallpaperType,
                "portrait",page,15)
        if (result.body() != null) {
            wallpaperLiveData.postValue(result.body())
            stateLiveData.postValue(State.Success)
        }
    }

    override suspend fun getTrendingWallpapers(page: Int) {
        stateLiveData.postValue(State.Loading)
        val result = wallpapersService.getCurated(page,41)
        if (result.body() != null) {
            curatedWallpaperLiveData.postValue(result.body())
            stateLiveData.postValue(State.Success)
        }
    }

}