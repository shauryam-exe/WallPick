package com.code.wallpick.ui.home

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.code.wallpick.R
import java.io.*


class ViewWallpaperActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var homeButton: TextView
    lateinit var lockButton: TextView
    lateinit var bitmap: Bitmap
    lateinit var outputStream: FileOutputStream
    lateinit var animator: LottieAnimationView
    lateinit var animationText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_wallpaper)

        animator = findViewById(R.id.animation)
        imageView = findViewById(R.id.imageView)
        homeButton = findViewById(R.id.setAsHome)
        lockButton = findViewById(R.id.setAsLock)
        animationText = findViewById(R.id.animationText)

        val url = intent.getStringExtra("URL")
        Glide.with(this).load(url).centerCrop().into(imageView)
        val avgColor = intent.getStringExtra("avgColor")
        window.statusBarColor = Color.parseColor(avgColor)
        Log.d("avg",avgColor!!)

        val wallpaperManager = WallpaperManager.getInstance(this)

        homeButton.setOnClickListener {
            val drawable = imageView.drawable
            bitmap = drawable.toBitmap()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                wallpaperManager.setBitmap(bitmap,null,true,WallpaperManager.FLAG_SYSTEM)
            } else {
                wallpaperManager.setBitmap(bitmap)
            }
            homeButton.isClickable = false
            startAnimation()
//            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//              saveImage()
//            } else {
//                askPersmission()
//            }
        }

        lockButton.setOnClickListener {
            val drawable = imageView.drawable
            val bitmap = drawable.toBitmap()
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                wallpaperManager.setBitmap(bitmap,null,true,WallpaperManager.FLAG_LOCK)
//            } else {
//                wallpaperManager.setBitmap(bitmap)
//            }
//            startAnimation()
//            lockButton.isClickable = false
            saveImage("favourite",bitmap)
            //startActivity(Intent(this,LoadWallpaperActivity::class.java))
        }
    }

    private fun startAnimation() {
        animator.setAnimation(R.raw.check)
        animator.loop(false)
        animator.playAnimation()
        animator.addAnimatorListener(object :AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {
                animationText.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(p0: Animator?) {
                animator.visibility = View.GONE
                animationText.visibility = View.GONE
                this@ViewWallpaperActivity.finish()
            }

            override fun onAnimationCancel(p0: Animator?) {
                TODO("Not yet implemented")
            }

            override fun onAnimationRepeat(p0: Animator?) {
                TODO("Not yet implemented")
            }
        })
    }


    private fun saveImage(folder: String, bmp: Bitmap) {
        try {
            val time = System.currentTimeMillis()
            var file = File(filesDir, folder)
            if (!file.exists()) {
                file.mkdir()
            }
            file = File(file, "$time")
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
            Log.i("Image Saving", "Image saved.")
        } catch (e: Exception) {
            Log.i("Image Saving", "Failed to save image.")
        }
    }
}