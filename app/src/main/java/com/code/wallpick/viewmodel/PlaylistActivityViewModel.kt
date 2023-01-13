package com.code.wallpick.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.code.wallpick.data.PlaylistRepository
import java.io.File

class PlaylistActivityViewModel(private val repository: PlaylistRepository): ViewModel() {

    private val wallpapersLiveData = MutableLiveData<ArrayList<File>>()

    val wallpapers: LiveData<ArrayList<File>>
        get() = wallpapersLiveData

    fun loadPlaylist(fileName: String) {
        val result = repository.loadPlaylist(fileName)
        wallpapersLiveData.postValue(result.toCollection(ArrayList()))
    }




}