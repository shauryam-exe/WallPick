package com.code.wallpick.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.wallpick.data.WallpaperRepository
import com.code.wallpick.data.model.PhotoList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: WallpaperRepository): ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTrendingWallpapers()
        }
    }

    val wallpapers: LiveData<PhotoList>
    get() = repository.wallpapers
}