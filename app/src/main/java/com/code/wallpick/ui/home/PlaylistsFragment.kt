package com.code.wallpick.ui.home


import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code.wallpick.R
import com.code.wallpick.adapter.PlaylistAdapter
import com.code.wallpick.data.PlaylistRepositoryImpl
import com.code.wallpick.ui.playlist.PlaylistActivity
import com.code.wallpick.viewmodel.PlaylistFragmentViewModel
import com.code.wallpick.viewmodel.utils.PlaylistViewModelFactory
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputLayout


class PlaylistsFragment : Fragment(), PlaylistAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlaylistAdapter
    private lateinit var viewModel: PlaylistFragmentViewModel
    private lateinit var fab: ExtendedFloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_playlists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab = view.findViewById(R.id.extendedFAB)

        initRecyclerView()
        intiViewModel()
        initScrollListener()
        initFAB()

    }


    private fun initRecyclerView() {
        Log.d("playlist", "Created recycler View")

        recyclerView = requireView().findViewById(R.id.playlist_recycler_view)
        adapter = PlaylistAdapter(requireContext(),this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun intiViewModel() {
        Log.d("playlist", "Created adapter")
        val repo = PlaylistRepositoryImpl()
        viewModel = ViewModelProvider(
            requireActivity(),
            PlaylistViewModelFactory(repo)
        )
            .get(PlaylistFragmentViewModel::class.java)
        viewModel.getListOfPlaylist()
        viewModel.playlistsLiveData.observe(viewLifecycleOwner) {
            adapter.updateList(it)
            Log.d("playlist", it.size.toString())
        }
    }

    private fun initScrollListener() {

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    fab.extend()
                } else {
                    fab.shrink()
                }
            }
        })
    }


    private fun initFAB() {
        fab.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(context)

            val viewInflated: View = LayoutInflater.from(context)
                .inflate(R.layout.dialog_add_playlist, getView() as ViewGroup?, false)

            val alertDialog = builder.create()
            alertDialog.setView(viewInflated,0,0,0,0)
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            var inputText = ""
            val button = viewInflated.findViewById<TextView>(R.id.positive_button)
            val inputLayout = viewInflated.findViewById<TextInputLayout>(R.id.inputLayout)
            val input = inputLayout.editText!!

            button.setOnClickListener {
                inputText = input.text.toString()
                if (inputText.isEmpty() || inputText.isBlank()) {
                    inputLayout.error = "Enter Name"
                } else if (fileAlreadyExists(inputText)) {
                    inputLayout.error = "Playlist Already Exists"
                } else {
                    val intent = Intent(context,PlaylistActivity::class.java)
                    intent.putExtra("file",inputText)
                    startActivity(intent)
                    alertDialog.dismiss()
                }
            }

            alertDialog.show()
        }
    }

    private fun fileAlreadyExists(name: String): Boolean {
        if (viewModel.playlistsLiveData.value.isNullOrEmpty()) return false

        for (file in viewModel.playlistsLiveData.value!!) {
            if (file.name.lowercase().equals(name.lowercase())) return true
        }
        return false
    }

    override fun onClick(filePath: String) {
        val intent = Intent(context, PlaylistActivity::class.java)
        intent.putExtra("file",filePath)
        startActivity(intent)
    }
}