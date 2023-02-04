package com.code.wallpick.adapter

import android.content.Context
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
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
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var photoList: ArrayList<File> = ArrayList()//arrayListOf(File(""))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("playlist adapter", "onCreate Called")
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == 0) {
            return FirstItemViewHolder(
                inflater.inflate(
                    R.layout.item_playlist_activity,
                    parent,
                    false
                )
            )
        }
        return PlaylistActivityViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_playlist_activity, parent, false)
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0
        else 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FirstItemViewHolder -> {
                holder.setImage()
            }
            is PlaylistActivityViewHolder -> {
                holder.setImage(photoList[position - 1],position)
            }
        }
    }

    override fun getItemCount(): Int {
        return photoList.size + 1
    }

    fun updateItems(images: List<File>) {
        Log.d("playlist adapter", "updateItems Called")
        photoList.clear()
//        photoList.add(File("abc"))
        photoList.addAll(images)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        // Remove the item from the data source
        photoList.removeAt(position)
        // Notify the adapter of the change
        notifyItemRemoved(position)
        notifyDataSetChanged()
        notifyItemRangeChanged(position,photoList.size)
    }


    inner class PlaylistActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.image_view)
        val cardView = itemView.findViewById<CardView>(R.id.card_view)

        fun setImage(file: File, position: Int) {

            Glide.with(imageView)
                .load(file)
                //.placeholder(colorDrawable)
                .into(imageView)
            itemView.setOnClickListener {
                listener.onClick()
            }

            itemView.setOnLongClickListener {
                listener.onLongClick(file,position-1)
                true
            }

        }
    }

    inner class FirstItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.image_view)
        val cardView = itemView.findViewById<CardView>(R.id.card_view)

        fun setImage() {
            cardView.elevation = 0f
            Glide.with(context)
                .load(R.drawable.add_icon_image)
                .into(imageView)
            cardView.setOnClickListener {
                listener.onAddClick()
            }
        }
    }

    interface OnItemClickListener {
        fun onClick()
        fun onLongClick(file: File, position: Int)
        fun onAddClick()
    }
}