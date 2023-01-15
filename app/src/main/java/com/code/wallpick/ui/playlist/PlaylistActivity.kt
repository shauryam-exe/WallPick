package com.code.wallpick.ui.playlist

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.code.wallpick.R
import com.code.wallpick.adapter.PlaylistActivityAdapter
import com.code.wallpick.data.PlaylistRepositoryImpl
import com.code.wallpick.viewmodel.PlaylistActivityViewModel
import com.code.wallpick.viewmodel.utils.PlaylistActivityViewModelFactory
import java.io.File

class PlaylistActivity : AppCompatActivity(), PlaylistActivityAdapter.OnItemClickListener {

    private lateinit var viewModel: PlaylistActivityViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlaylistActivityAdapter
    private lateinit var addWallpaperDialog: AddWallpaperDialogFragment
    private lateinit var toolbar: Toolbar

    private lateinit var playlist: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)
        playlist = intent.getStringExtra("file")!!

        toolbar = findViewById(R.id.toolbar)
        val toolbarTextView = toolbar.getChildAt(0) as TextView
        toolbarTextView.text = playlist

        addWallpaperDialog = AddWallpaperDialogFragment()

        initRecyclerView()
        initViewModel()


        viewModel.loadPlaylist(playlist)
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
        val bundle = Bundle()
        bundle.putString("TEXT", playlist)
        addWallpaperDialog.arguments = bundle
        addWallpaperDialog.show(supportFragmentManager,AddWallpaperDialogFragment.TAG)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadPlaylist(playlist)
    }
}