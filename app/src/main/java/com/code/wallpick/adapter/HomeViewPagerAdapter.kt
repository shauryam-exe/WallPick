package com.code.wallpick.adapter

import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.code.wallpick.ui.home.PlaylistsFragment
import com.code.wallpick.ui.home.TrendingFragment

class HomeViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return TrendingFragment()
        } else return PlaylistsFragment()
    }

}