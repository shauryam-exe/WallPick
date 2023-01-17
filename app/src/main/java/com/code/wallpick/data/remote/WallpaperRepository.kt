package com.code.wallpick.data.remote

import androidx.lifecycle.LiveData
import com.code.wallpick.data.State
import com.code.wallpick.data.model.PhotoList

interface WallpaperRepository {

    val curatedWallpapers: LiveData<PhotoList>

    val state: LiveData<State>

    suspend fun getWallpapers(wallpaperType: String, page: Int)

    suspend fun getTrendingWallpapers(page: Int)

}