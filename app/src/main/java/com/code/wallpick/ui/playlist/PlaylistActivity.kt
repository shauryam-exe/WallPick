package com.code.wallpick.ui.playlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.code.wallpick.R
import com.code.wallpick.adapter.PlaylistActivityAdapter
import com.code.wallpick.data.PlaylistRepositoryImpl
import com.code.wallpick.viewmodel.PlaylistActivityViewModel
import com.code.wallpick.viewmodel.utils.PlaylistActivityViewModelFactory

class PlaylistActivity : AppCompatActivity(), PlaylistActivityAdapter.OnItemClickListener {

    private lateinit var viewModel: PlaylistActivityViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlaylistActivityAdapter
    private lateinit var addWallpaperDialog: AddWallpaperDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)

        addWallpaperDialog = AddWallpaperDialogFragment()

        initRecyclerView()
        initViewModel()


        val fileName = intent.getStringExtra("file")
        Log.d("PlaylistActivity",fileName.toString())
        viewModel.loadPlaylist(fileName!!)
        viewModel.wallpapers.observe(this) {
            adapter.updateItems(it)
        }
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.playlist_activity_recycler_view)
        adapter = PlaylistActivityAdapter(this,this)
        val layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }

    private fun initViewModel() {
        Log.d("playlist", "Created adapter")
        val repo = PlaylistRepositoryImpl()
        viewModel = ViewModelProvider(
            this,
            PlaylistActivityViewModelFactory(repo)
        ).get(PlaylistActivityViewModel::class.java)

    }

    override fun onClick() {
        TODO("Not yet implemented")
    }

    override fun onAddClick() {
        addWallpaperDialog.show(supportFragmentManager,AddWallpaperDialogFragment.TAG)
    }
}