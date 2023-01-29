package com.code.wallpick.data.local

import android.graphics.Bitmap
import java.io.File

interface PlaylistRepository {

    fun loadPlaylist(playlistName: String): Array<File>

    fun listOfPlaylists(): Array<File>

    fun saveWallpaper(folder: String, bmp: Bitmap, name: String, filesDir: File)
}