package com.code.wallpick.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.code.wallpick.App
import com.code.wallpick.data.PlaylistRepository
import java.io.File

class PlaylistActivityViewModel(private val repository: PlaylistRepository): ViewModel() {

    private val wallpapersLiveData = MutableLiveData<ArrayList<File>>()

    val wallpapers: LiveData<ArrayList<File>>
        get() = wallpapersLiveData

    fun loadPlaylist(fileName: String) {
        val result = repository.loadPlaylist(fileName)
        Log.d("playlist", result.size.toString())
        wallpapersLiveData.value?.clear()
        Log.d("playlist viewModel", wallpapersLiveData.value?.size.toString())
        wallpapersLiveData.postValue(result.toCollection(ArrayList()))
    }
}