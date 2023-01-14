package com.code.wallpick.adapter

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.code.wallpick.R
import java.io.File


class PlaylistActivityAdapter(
    val context: Context,
    val listener: PlaylistActivityAdapter.OnItemClickListener
) : RecyclerView.Adapter<PlaylistActivityAdapter.PlaylistActivityViewHolder>() {

    var photoList: ArrayList<File> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistActivityViewHolder {
        return PlaylistActivityViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_playlist_activity, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlaylistActivityViewHolder, position: Int) {
            holder.setImage(photoList[position], holder.adapterPosition)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    fun updateItems(images: List<File>) {
        val startPosition = photoList.size
        photoList.addAll(images)
        notifyItemRangeInserted(startPosition, images.size)
    }


    inner class PlaylistActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.image_view)
        val cardView = itemView.findViewById<CardView>(R.id.card_view)

        fun setImage(file: File, position: Int) {

//            imageView.setBackgroundColor(Color.parseColor(photo.avg_color))
//            val colorDrawable = ColorDrawable(Color.parseColor(photo.avg_color))
//            colorDrawable.setBounds(0, 0, photo.width, photo.height)

            if (position == 0) {
                cardView.elevation = 0f
                Glide.with(context)
                    .load(R.drawable.add_icon_image)
                    .fitCenter()
                    .into(imageView)
                itemView.setOnClickListener {
                    listener.onAddClick()
                }
            } else {
            Glide.with(imageView)
                .load(file)
                //.placeholder(colorDrawable)
                .fitCenter()
                .into(imageView)
                itemView.setOnClickListener {
                    listener.onClick()
                }
            }


        }
    }

    interface OnItemClickListener {
        fun onClick()
        fun onAddClick()
    }
}