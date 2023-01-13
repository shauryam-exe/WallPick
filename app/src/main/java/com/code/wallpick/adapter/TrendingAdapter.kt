package com.code.wallpick.adapter

import android.animation.Animator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.code.wallpick.R
import com.code.wallpick.data.model.Photo
import com.pedromassango.doubleclick.DoubleClick
import com.pedromassango.doubleclick.DoubleClickListener


class TrendingAdapter(val context: Context, val listener: OnItemClickListener) :
    RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder>() {

    var photoList: ArrayList<Photo> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        return TrendingViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_trending, parent, false)
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
        notifyItemRangeInserted(startPosition, images.size)
    }



    inner class TrendingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.image_view)
        val animator = itemView.findViewById<LottieAnimationView>(R.id.save_animation)
        val cardView = itemView.findViewById<CardView>(R.id.card_view)
        //val textView = itemView.findViewById<TextView>(R.id.explore_text)

        fun setImage(photo: Photo, position: Int) {

            imageView.setBackgroundColor(Color.parseColor(photo.avg_color))
            val colorDrawable = ColorDrawable(Color.parseColor(photo.avg_color))
            colorDrawable.setBounds(0, 0, photo.width, photo.height)
            Glide.with(imageView)
                .load(photo.src.portrait)
                .placeholder(colorDrawable)
                .fitCenter()
                .into(imageView)

            val customClick = DoubleClick(object : DoubleClickListener {
                override fun onSingleClick(view: View?) {
                    Log.d("Click", "Single Click")
                    listener.onSingleClick(photoList[position])
                }

                override fun onDoubleClick(view: View?) {
                    val bmp = imageView.drawable.toBitmap()
                    val result = listener.onDoubleClick(bmp, photoList[position])
                    if (result) {
                        startAnimation()
                    } else {
                        TODO("Put an Error animation, Not required now")
                    }
                }
            }, 300)

            itemView.setOnClickListener(customClick)
        }

        private fun startAnimation() {
            if (animator.visibility == View.GONE)
                animator.visibility = View.VISIBLE
            animator.setAnimation(R.raw.like)
            animator.loop(false)
            animator.playAnimation()

            animator.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        animator.visibility = View.GONE
                    },500)
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }
            })
        }

    }

    interface OnItemClickListener {
        fun onSingleClick(photo: Photo)
        fun onDoubleClick(bmp: Bitmap, photo: Photo): Boolean

    }
}