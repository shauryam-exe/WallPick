package com.code.wallpick.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.code.wallpick.R
import com.code.wallpick.adapter.TrendingAdapter
import com.code.wallpick.api.RetrofitHelper
import com.code.wallpick.api.WallpapersService
import com.code.wallpick.data.WallpaperRepository
import com.code.wallpick.viewmodel.HomeViewModel
import com.code.wallpick.viewmodel.utils.HomeViewModelFactory


class TrendingFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.trending_recycler_view)
        val adapter = TrendingAdapter(requireActivity())
        recyclerView.adapter = adapter
        val layout = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layout



        val wallpapersService = RetrofitHelper.getInstance().create(WallpapersService::class.java)
        val repo = WallpaperRepository(wallpapersService)
        viewModel =
            ViewModelProvider(this, HomeViewModelFactory(repo)).get(HomeViewModel::class.java)
        viewModel.wallpapers.observe(viewLifecycleOwner) {
            Log.d("pexels","${it.photos[1].url}")
            adapter.updateItems(it.photos)
        }
    }

}