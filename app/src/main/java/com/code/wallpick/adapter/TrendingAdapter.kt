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
import com.bumptech.glide.request.RequestOptions
import com.code.wallpick.R
import com.code.wallpick.data.model.Photo
import com.pedromassango.doubleclick.DoubleClick
import com.pedromassango.doubleclick.DoubleClickListener
import kotlin.math.roundToInt


class TrendingAdapter(val context: Context, val listener: OnItemClickListener) :
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
        val startPosition = photoList.size
        photoList.addAll(images)
        notifyItemRangeInserted(startPosition,images.size)
    }


    inner class TrendingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener{
        val imageView = itemView.findViewById<ImageView>(R.id.image_view)
        val cardView = itemView.findViewById<CardView>(R.id.card_view)
        //val textView = itemView.findViewById<TextView>(R.id.explore_text)

        fun setImage(photo: Photo, position: Int) {

            //Log.d("adapter", "$position")
//            if (position == 0) {
//                Glide.with(context)
//                    .load(R.drawable.explore)
//                    .into(imageView)
//                textView.visibility = View.VISIBLE
//            } else {
            imageView.setBackgroundColor(Color.parseColor(photo.avg_color))
                val colorDrawable = ColorDrawable(Color.parseColor(photo.avg_color))
                colorDrawable.setBounds(0,0,photo.width,photo.height)
                    Glide.with(imageView)
                        .load(photo.src.portrait)
                        .placeholder(colorDrawable)
                        .fitCenter()
                        .into(imageView)
            //        textView.visibility = View.GONE
            //}
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            val customClick = DoubleClick(object : DoubleClickListener {
                override fun onSingleClick(view: View?) {
                    listener.onSingleClick(photoList[position])
                }

                override fun onDoubleClick(view: View?) {
                    val bmp = imageView.drawable.toBitmap()
                    listener.onDoubleClick(bmp, photoList[position])
                }

            }, 300)
            v!!.setOnClickListener(customClick)
        }


    }

    interface OnItemClickListener{
        fun onSingleClick(photo: Photo)
        fun onDoubleClick(bmp: Bitmap, photo: Photo)

    }
}