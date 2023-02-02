package com.code.wallpick.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.code.wallpick.data.local.PlaylistRepository
import java.io.File

class PlaylistActivityViewModel(private val repository: PlaylistRepository): ViewModel() {

    private val wallpapersLiveData = MutableLiveData<ArrayList<File>>()

    val wallpapers: LiveData<ArrayList<File>>
        get() = wallpapersLiveData

    fun loadPlaylist(fileName: String) {
        val result = repository.loadPlaylist(fileName)
        Log.d("saved", result.size.toString())
        wallpapersLiveData.value?.clear()
        Log.d("saved playlist viewModel", wallpapersLiveData.value?.size.toString())
//        wallpapersLiveData.postValue(result.toCollection(ArrayList()))
        wallpapersLiveData.value = result.toCollection(ArrayList())
        Log.d("loaded playlist viewModel", wallpapersLiveData.value?.size.toString())
    }
}