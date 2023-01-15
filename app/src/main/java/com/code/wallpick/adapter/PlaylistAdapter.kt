package com.code.wallpick.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code.wallpick.R
import com.code.wallpick.data.model.Playlist
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur
import java.io.File

class PlaylistAdapter(val context: Context, val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    var playlists = ArrayList<Playlist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_playlist, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.setPlaylist(position)
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    fun updateList(list: ArrayList<Playlist>) {
        val startPosition = playlists.size
        playlists.addAll(list)
        notifyItemRangeInserted(startPosition, list.size)
    }

    inner class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.playlist_item_image)
        val blurView = itemView.findViewById<BlurView>(R.id.playlist_item_blur)
        val textView = itemView.findViewById<TextView>(R.id.playlist_item_text)
        val rootView = itemView.findViewById<CardView>(R.id.playlist_item_card)

        fun setPlaylist(position: Int) {
            val playlist = playlists[position]
            val file = File(playlist.coverImagePath)
            if (playlist.coverImagePath.isNotBlank())
                Glide.with(context)
                    .load(file)
                    .centerCrop()
                    .into(imageView)

            blurView.setupWith(rootView, RenderScriptBlur(context))
                .setBlurRadius(20f)
            textView.text = playlist.name

            itemView.setOnClickListener {
                clickListener.onClick(playlist.name)
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(filePath: String)
    }

}