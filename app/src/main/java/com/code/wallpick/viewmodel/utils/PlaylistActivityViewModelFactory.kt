package com.code.wallpick.viewmodel.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.code.wallpick.data.local.PlaylistRepository
import com.code.wallpick.viewmodel.PlaylistActivityViewModel

class PlaylistActivityViewModelFactory(private val repository: PlaylistRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaylistActivityViewModel(repository) as T
    }
}