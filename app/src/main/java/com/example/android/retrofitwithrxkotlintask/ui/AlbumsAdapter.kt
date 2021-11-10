package com.example.android.retrofitwithrxkotlintask.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.android.retrofitwithrxkotlintask.R
import com.example.android.retrofitwithrxkotlintask.databinding.ItemAlbumTitleBinding
import com.example.android.retrofitwithrxkotlintask.models.Album

class AlbumsAdapter(val albums: List<Album>): RecyclerView.Adapter<AlbumsAdapter.AlbumsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsAdapter.AlbumsHolder {
        return AlbumsHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AlbumsAdapter.AlbumsHolder, position: Int) {
        val item = albums[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = albums.size

    class AlbumsHolder(val binding: ItemAlbumTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album){
            binding.album = album
        }

        companion object{
            fun from(parent: ViewGroup): AlbumsHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAlbumTitleBinding.inflate(layoutInflater,parent,false)
                return AlbumsHolder(binding)
            }
        }
    }

}