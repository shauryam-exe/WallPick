package com.code.wallpick.viewmodel.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.code.wallpick.data.WallpaperRepository
import com.code.wallpick.viewmodel.HomeViewModel

class HomeViewModelFactory(private val repository: WallpaperRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}