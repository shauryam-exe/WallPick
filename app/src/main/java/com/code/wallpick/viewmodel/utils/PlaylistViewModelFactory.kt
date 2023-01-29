package com.code.wallpick.viewmodel.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.code.wallpick.data.local.PlaylistRepository
import com.code.wallpick.viewmodel.PlaylistFragmentViewModel

class PlaylistViewModelFactory(private val repository: PlaylistRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaylistFragmentViewModel(repository) as T
    }
}