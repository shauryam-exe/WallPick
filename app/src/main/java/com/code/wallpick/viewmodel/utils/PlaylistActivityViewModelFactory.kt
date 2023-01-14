package com.code.wallpick.viewmodel.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.code.wallpick.data.PlaylistRepository
import com.code.wallpick.viewmodel.PlaylistActivityViewModel
import com.code.wallpick.viewmodel.PlaylistFragmentViewModel

class PlaylistActivityViewModelFactory(private val repository: PlaylistRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaylistActivityViewModel(repository) as T
    }
}