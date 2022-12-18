package com.code.wallpick.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code.wallpick.R
import com.code.wallpick.adapter.PlaylistAdapter
import com.code.wallpick.data.PlaylistRepositoryImpl
import com.code.wallpick.viewmodel.PlaylistViewModel
import com.code.wallpick.viewmodel.utils.PlaylistViewModelFactory

class PlaylistsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlaylistAdapter
    private lateinit var viewModel: PlaylistViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_playlists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        intiViewModel()
    }

    private fun initRecyclerView() {
        Log.d("playlist","Created recycler View")

        recyclerView = requireView().findViewById(R.id.playlist_recycler_view)
        adapter = PlaylistAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun intiViewModel() {
        Log.d("playlist","Created adapter")
        val repo = PlaylistRepositoryImpl()
        viewModel = ViewModelProvider(requireActivity(),
            PlaylistViewModelFactory(repo))
            .get(PlaylistViewModel::class.java)
        viewModel.getListOfPlaylist()
        viewModel.playlistsLiveData.observe(viewLifecycleOwner) {
            adapter.updateList(it)
            Log.d("playlist",it.size.toString())
        }
    }
}