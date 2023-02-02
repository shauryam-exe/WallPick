package com.code.wallpick.ui.playlist

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.code.wallpick.App
import com.code.wallpick.R
import com.code.wallpick.adapter.PlaylistActivityAdapter
import com.code.wallpick.data.local.PlaylistRepositoryImpl
import com.code.wallpick.service.HomeScreenReceiver
import com.code.wallpick.service.ShakeService
import com.code.wallpick.viewmodel.PlaylistActivityViewModel
import com.code.wallpick.viewmodel.utils.PlaylistActivityViewModelFactory
import java.io.FileInputStream

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
        window.statusBarColor = getColor(R.color.dark_blue)

        playlist = intent.getStringExtra("file")!!

        toolbar = findViewById(R.id.toolbar)
        val toolbarTextView = toolbar.getChildAt(0) as TextView
        toolbarTextView.text = playlist
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""

        addWallpaperDialog = AddWallpaperDialogFragment()

        adapter = PlaylistActivityAdapter(this, this)

        initRecyclerView()
        initViewModel()

        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
        }, 80)

        viewModel.loadPlaylist(playlist)
        viewModel.wallpapers.observe(this) {
            adapter.updateItems(it)
        }
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.playlist_activity_recycler_view)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter


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

    }

    override fun onAddClick() {
        val bundle = Bundle()
        Log.d("click","onAdd Click working")
        bundle.putString("TEXT", playlist)
        addWallpaperDialog.arguments = bundle
        addWallpaperDialog.show(supportFragmentManager, AddWallpaperDialogFragment.TAG)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadPlaylist(playlist)
        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
        }, 80)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.playlist_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_wallpaper -> {
                setAsHomeWallpaper()
            }
            R.id.lock_wallpaper -> {
                setAsLockWallpaper()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setAsLockWallpaper() {
        val sharedPrefs = getSharedPreferences(App.PREFERENCES, Context.MODE_PRIVATE).edit()
        sharedPrefs.putString(App.LOCK_PLAYLIST, playlist)
        sharedPrefs.apply()
        if (viewModel.wallpapers.value?.size != 0) {
            val file = viewModel.wallpapers.value!![0]
            val bitmap = BitmapFactory.decodeStream(FileInputStream(file))
            val wallpaperManager = WallpaperManager.getInstance(this)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
            } else {
                wallpaperManager.setBitmap(bitmap)
            }
        }
    }

    private fun setAsHomeWallpaper() {
        val sharedPrefs = getSharedPreferences(App.PREFERENCES, Context.MODE_PRIVATE).edit()
        sharedPrefs.putString(App.HOME_PLAYLIST, playlist)
        sharedPrefs.apply()
        if (viewModel.wallpapers.value?.size != 0) {
            val file = viewModel.wallpapers.value!![0]
            val bitmap = BitmapFactory.decodeStream(FileInputStream(file))
            val wallpaperManager = WallpaperManager.getInstance(this)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                HomeScreenReceiver.playlistChanged = true
            } else {
                wallpaperManager.setBitmap(bitmap)
            }
        }
    }

}