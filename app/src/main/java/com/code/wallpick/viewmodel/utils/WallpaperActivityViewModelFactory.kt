package com.code.wallpick.viewmodel.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.code.wallpick.data.remote.WallpaperRepositoryImpl
import com.code.wallpick.viewmodel.WallpaperActivityViewModel

class WallpaperActivityViewModelFactory(private val repository: WallpaperRepositoryImpl): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WallpaperActivityViewModel(repository) as T
    }
}