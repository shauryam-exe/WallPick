package com.code.wallpick.ui.home

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.code.wallpick.R
import com.code.wallpick.data.model.InternalStoragePhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream

class LoadWallpaperActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var inputStream: FileInputStream

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_wallpaper)
        imageView = findViewById(R.id.loadImageView)

        loadImages()



    }

    private fun loadImages(){
        val director: File = File(
            Environment.getExternalStorageDirectory().absolutePath +
                    File.separator + "/SaveImage"
        )
        val directory = File(filesDir,"SaveImage")

        Log.d("shimonaL","${directory.exists()}")
        Log.d("shimonaL","$directory")


        if (directory.exists()) {

            val files: Array<File> = directory.listFiles()!!
            Log.d("shimonaL","${files.size}")

            directory.walkTopDown().forEach { file ->
                Log.d("shimonaP",file.absolutePath)
//                inputStream = openFileInput(file.name)
//                val bitmap = inputStream.read()
//                Log.d("shimonaL","${bitmap.toString()}")
            }

            for (file in files) {

            }

        }
    }
}