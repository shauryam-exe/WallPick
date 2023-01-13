package com.code.wallpick.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.code.wallpick.data.PlaylistRepository
import com.code.wallpick.data.model.Playlist
import java.io.File

class PlaylistFragmentViewModel(private val repository: PlaylistRepository): ViewModel() {

    val playlistsLiveData = MutableLiveData<ArrayList<Playlist>>()

    fun getListOfPlaylist() {
        val playlists = repository.listOfPlaylists()

        val playlistArray : ArrayList<Playlist> = ArrayList()

        for (i in  playlists) {
            playlistArray.add(Playlist(i.name,i.path,getImagePath(i.path)))
        }

        playlistsLiveData.value = playlistArray
    }

    private fun getImagePath(file: String): String {
        try {
            val folder = File(file)
            val image = folder.listFiles()?.get(0)?.path
            return image ?: ""
        } catch (e: ArrayIndexOutOfBoundsException) {
            Log.d("Playlist View Model","$file does not have any image")
            return ""
        }
    }
}