package com.code.wallpick.ui.playlist

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.code.wallpick.R
import com.code.wallpick.adapter.WallpaperStackAdapter
import com.code.wallpick.data.remote.RetrofitHelper
import com.code.wallpick.data.remote.WallpapersService
import com.code.wallpick.data.remote.WallpaperRepositoryImpl
import com.code.wallpick.viewmodel.WallpaperActivityViewModel
import com.code.wallpick.viewmodel.utils.WallpaperActivityViewModelFactory
import com.yuyakaido.android.cardstackview.*

class WallpaperActivity : AppCompatActivity(), CardStackListener {

    private lateinit var viewModel: WallpaperActivityViewModel
    private lateinit var stackView: CardStackView
    private lateinit var adapter: WallpaperStackAdapter
    private lateinit var layoutManager: CardStackLayoutManager

    private var page = (0..50).random();
    private lateinit var wallpaperType: String
    private lateinit var playlistName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)
        wallpaperType = intent.getStringExtra("collection")!!
        playlistName = intent.getStringExtra("playlist")!!

        initStackView()
        Log.d("wallpaper",wallpaperType)
        initViewModel()
        viewModel.getWallpapers(wallpaperType,page++)

    }

    private fun initStackView() {
        stackView = findViewById(R.id.stack_view)
        adapter = WallpaperStackAdapter(this)
        layoutManager = CardStackLayoutManager(this, this).apply {
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }
        stackView.layoutManager = layoutManager
        stackView.adapter = adapter
        stackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun initViewModel() {
        val wallpapersService = RetrofitHelper.getInstance().create(WallpapersService::class.java)
        val repo = WallpaperRepositoryImpl(wallpapersService)
        viewModel = ViewModelProvider(this,
            WallpaperActivityViewModelFactory(repo))
            .get(WallpaperActivityViewModel::class.java)
        viewModel.wallpapers.observe(this) {
            adapter.updateItems(it.photos)
        }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {
        if (direction == Direction.Right) {
            val position = layoutManager.topPosition
            val photo = adapter.photoList[position-1]
            val id = photo.id.toString()
            var bmp: Bitmap
            Glide.with(this)
                .asBitmap()
                .load(photo.src.portrait)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        bmp = resource
                        viewModel.saveWallpaper(playlistName,bmp,id)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })


            Log.d("Stack",photo.url)
        }
    }

    override fun onCardRewound() {

    }


    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {
        if (position == adapter.photoList.size-3) {
            viewModel.getWallpapers(wallpaperType,page++)
            Log.d("wallpaper","onCardAppeared Called")
        }
    }

    override fun onCardDisappeared(view: View?, position: Int) {

    }

}