package com.code.wallpick.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.code.wallpick.R
import com.code.wallpick.ui.intro.OnBoardingFragment1
import com.code.wallpick.ui.intro.OnBoardingFragment2

class OnBoardingAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return OnBoardingFragment1()
        } else {
            return OnBoardingFragment2()
        }
    }


}