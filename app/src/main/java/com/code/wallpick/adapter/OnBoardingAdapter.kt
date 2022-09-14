package com.code.wallpick.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.code.wallpick.R

class OnBoardingAdapter: RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {


    private val NUM_PAGES = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.on_boarding_container,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        when (position) {
            0 -> {
                holder.layout1.visibility = View.VISIBLE
                holder.layout2.visibility = View.GONE
            }
            1 -> {
                holder.layout1.visibility = View.GONE
                holder.layout2.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int = NUM_PAGES

    class OnBoardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout1 = itemView.findViewById<ConstraintLayout>(R.id.splashLayout1)
        val layout2 = itemView.findViewById<ConstraintLayout>(R.id.splashLayout2)
    }

}