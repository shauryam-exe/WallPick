package com.code.wallpick.ui.playlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.code.wallpick.R
import com.code.wallpick.data.PlaylistRepositoryImpl
import com.code.wallpick.viewmodel.PlaylistActivityViewModel
import com.code.wallpick.viewmodel.PlaylistFragmentViewModel
import com.code.wallpick.viewmodel.utils.PlaylistActivityViewModelFactory
import com.code.wallpick.viewmodel.utils.PlaylistViewModelFactory

class PlaylistActivity : AppCompatActivity() {

    private lateinit var viewModel: PlaylistActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)

        initViewModel()

        val fileName = intent.getStringExtra("file")

        viewModel.loadPlaylist(fileName!!)
        viewModel.wallpapers.observe(this) {
            
        }
    }

    private fun initViewModel() {
        Log.d("playlist", "Created adapter")
        val repo = PlaylistRepositoryImpl()
        viewModel = ViewModelProvider(
            this,
            PlaylistActivityViewModelFactory(repo)
        ).get(PlaylistActivityViewModel::class.java)

    }
}