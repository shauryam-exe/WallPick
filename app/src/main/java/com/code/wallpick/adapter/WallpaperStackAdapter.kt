package com.code.wallpick.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code.wallpick.R
import com.code.wallpick.data.model.Photo

class WallpaperStackAdapter(val context: Context) :
    RecyclerView.Adapter<WallpaperStackAdapter.WallpaperStackViewHolder>() {

    val photoList: ArrayList<Photo> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperStackViewHolder {
        return WallpaperStackViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_wallpaper_stack, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WallpaperStackViewHolder, position: Int) {
        holder.setImage(position)
    }

    override fun getItemCount(): Int {
        return photoList.size - 1
    }

    fun updateItems(images: List<Photo>) {
        val startPosition = photoList.size
        photoList.addAll(images)
        notifyItemRangeInserted(startPosition, images.size)
    }


    inner class WallpaperStackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.stack_image_view)
        val cardView = itemView.findViewById<CardView>(R.id.stack_card_view)

        fun setImage(position: Int) {
            val photo = photoList[position]
            imageView.setBackgroundColor(Color.parseColor(photo.avg_color))
            val colorDrawable = ColorDrawable(Color.parseColor(photo.avg_color))
            colorDrawable.setBounds(0, 0, photo.width, photo.height)

            Glide.with(context)
                .load(photoList[position].src.portrait)
                .placeholder(colorDrawable)
                .centerCrop()
                .into(imageView)
        }
    }

}