package com.code.wallpick.adapter

import android.app.ActionBar
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code.wallpick.R
import com.code.wallpick.data.model.Photo
import org.w3c.dom.Text
import kotlin.math.roundToInt


class TrendingAdapter(val context: Context) :
    RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder>() {

    var photoList: ArrayList<Photo> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        return TrendingViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.trending_playlist_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        holder.setImage(photoList[position], holder.adapterPosition)
    }

    override fun getItemCount(): Int {
        return photoList.size - 1
    }

    fun updateItems(images: List<Photo>) {
        photoList.addAll(images)
        notifyDataSetChanged()
    }

    fun dpToPx(dp: Int): Int {
        val density: Float = context.resources
            .displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }

    inner class TrendingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.image_view)
        val cardView = itemView.findViewById<CardView>(R.id.card_view)
        val textView = itemView.findViewById<TextView>(R.id.explore_text)

        fun setImage(photo: Photo, position: Int) {

            Log.d("adapter", "$position")
            if (position == 0) {
                Glide.with(context)
                    .load(R.drawable.explore)
                    .into(imageView)
                textView.visibility = View.VISIBLE
            } else {
                Glide.with(context)
                    .load(photo.src.portrait)
                    .fitCenter()
                    .into(imageView)
                textView.visibility = View.GONE
            }
        }
    }
}