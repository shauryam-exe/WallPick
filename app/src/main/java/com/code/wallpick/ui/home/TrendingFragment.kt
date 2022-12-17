package com.code.wallpick.ui.home

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.code.wallpick.R
import com.code.wallpick.adapter.TrendingAdapter
import com.code.wallpick.api.RetrofitHelper
import com.code.wallpick.api.WallpapersService
import com.code.wallpick.data.State
import com.code.wallpick.data.WallpaperRepository
import com.code.wallpick.data.model.Photo
import com.code.wallpick.viewmodel.HomeViewModel
import com.code.wallpick.viewmodel.utils.HomeViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


class TrendingFragment : Fragment(), TrendingAdapter.OnItemClickListener {

    private lateinit var viewModel: HomeViewModel
    lateinit var adapter: TrendingAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var loading: ConstraintLayout
    var page = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("fragment", "OnCreateView Called")
        return inflater.inflate(R.layout.fragment_trending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loading = view.findViewById(R.id.trending_loading_layout)


        initRecyclerView()

        initViewModel()

        initLoading()

        initScrollListener()

    }

    private fun initLoading() {
        viewModel.apiState.observe(viewLifecycleOwner) {
            when(it) {
                State.Loading -> {
                    loading.visibility = View.VISIBLE
                }
                State.Success -> {
                    loading.visibility = View.INVISIBLE
                }
                else -> {
                    loading.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun initRecyclerView() {
        recyclerView = requireView().findViewById(R.id.trending_recycler_view)
        adapter = TrendingAdapter(requireActivity(), this)
        recyclerView.adapter = adapter
        val layout = GridLayoutManager(requireContext(), 2)
        //val layout = GridLayoutManager(requireContext(),2)
        recyclerView.layoutManager = layout
        recyclerView.setHasFixedSize(true)
    }

    private fun initViewModel() {
        val wallpapersService = RetrofitHelper.getInstance().create(WallpapersService::class.java)
        val repo = WallpaperRepository(wallpapersService)
        viewModel =
            ViewModelProvider(
                requireActivity(),
                HomeViewModelFactory(repo)
            ).get(HomeViewModel::class.java)
        viewModel.wallpapers.observe(viewLifecycleOwner) {
            adapter.updateItems(it.photos)
        }
    }

    private fun initScrollListener() {
        val layoutManager = recyclerView.layoutManager as GridLayoutManager

        var scrollListenerOn = true
        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (scrollListenerOn && lastVisibleItemPosition == adapter.itemCount - 1) {
                    loadMore()
                    scrollListenerOn = false
                    val handler = Handler()
                    handler.postDelayed({
                       scrollListenerOn = true
                    }, 3000)
                }

            }
        })
    }

    private fun loadMore() {
            viewModel.loadTrendingWallpapers(page++)
    }


    override fun onSingleClick(photo: Photo) {
        Log.d("singleClick", "Single Click Working")

//        val intent = Intent(requireActivity(), ViewWallpaperActivity::class.java)
//        intent.putExtra("URL", photo.src.portrait)
//        intent.putExtra("avgColor", photo.avg_color)
//        startActivity(intent)
    }

    override fun onDoubleClick(bmp: Bitmap, photo: Photo): Boolean {
        Log.d("doubleClick", "Double Click Working")
        val dir = requireActivity().filesDir

        val result = viewModel.saveImage("Favs", bmp, photo.id.toString(), dir)
        return result
    }

    private fun saveImage(folder: String, bmp: Bitmap, name: String, filesDir: File) {
        try {
            val time = System.currentTimeMillis()
            var file = File(filesDir, folder)
            if (!file.exists()) {
                file.mkdir()
            }
            file = File(file, "$name.jpg")
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
            Log.i("Image Saving", "Image saved at $file")
        } catch (e: Exception) {
            Log.i("Image Saving", "Failed to save image.")
        }
    }

}