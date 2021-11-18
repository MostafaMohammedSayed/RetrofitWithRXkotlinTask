package com.example.android.retrofitwithrxkotlintask.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.android.retrofitwithrxkotlintask.R
import com.example.android.retrofitwithrxkotlintask.models.Album

class AlbumsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val albums = ArrayList<Album>()

    override fun getItemViewType(position: Int): Int {
        return if (albums.size == 0) {
            PLACE_HOLDER_VIEW_TYPE
        } else {
            ITEM_HOLDER_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PLACE_HOLDER_VIEW_TYPE -> LoadingViewHolder.from(parent)
            else -> AlbumsHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AlbumsHolder) {
            val album = albums[position]
            holder.bind(album)
        } else if (holder is LoadingViewHolder) {
            holder.bind()
        }
    }

    override fun getItemCount(): Int {
        return if (albums.size == 0) {
            10
        } else {
            albums.size
        }
    }

    class AlbumsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView = itemView.findViewById<TextView>(R.id.albumTitle)
        fun bind(album: Album) {
            textView.text = album.title
        }

        companion object {
            fun from(parent: ViewGroup): AlbumsHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val inflatedView = layoutInflater.inflate(R.layout.item_album_title, parent, false)
                return AlbumsHolder(inflatedView)
            }
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView = itemView.findViewById<TextView>(R.id.shimmerAlbumTitle)
        fun bind() {
            textView.background =
                ContextCompat.getDrawable(itemView.context, R.color.place_holder_background_color)
        }

        companion object {
            fun from(parent: ViewGroup): LoadingViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val inflatedView = layoutInflater.inflate(R.layout.shimmer_layout, parent, false)
                return LoadingViewHolder(inflatedView)
            }
        }
    }

    companion object {
        const val PLACE_HOLDER_VIEW_TYPE = 0
        const val ITEM_HOLDER_VIEW_TYPE = 1
    }
}