package com.code.wallpick.viewmodel.utils

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.code.wallpick.data.PlaylistRepository
import com.code.wallpick.data.model.Playlist
import com.code.wallpick.viewmodel.PlaylistViewModel

class PlaylistViewModelFactory(private val repository: PlaylistRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaylistViewModel(repository) as T
    }
}