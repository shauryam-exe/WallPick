package com.code.wallpick.viewmodel.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.code.wallpick.data.PlaylistRepository
import com.code.wallpick.data.WallpaperRepository
import com.code.wallpick.viewmodel.PlaylistFragmentViewModel
import com.code.wallpick.viewmodel.WallpaperActivityViewModel

class WallpaperActivityViewModelFactory(private val repository: WallpaperRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WallpaperActivityViewModel(repository) as T
    }
}