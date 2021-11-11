package com.example.android.retrofitwithrxkotlintask.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.retrofitwithrxkotlintask.R
import com.example.android.retrofitwithrxkotlintask.models.Album

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumsHolder>() {

    var albums =  ArrayList<Album>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsHolder {
        return AlbumsHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AlbumsHolder, position: Int) {
        val album = albums[position]
        holder.bind(album)
    }

    override fun getItemCount(): Int = albums.size

    class AlbumsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView = itemView.findViewById<TextView>(R.id.albumTitle)
        fun bind(album: Album){
            textView.text = album.title
        }

        companion object{
            fun from(parent: ViewGroup): AlbumsHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val inflatedView= layoutInflater.inflate(R.layout.item_album_title,parent,false)
                return AlbumsHolder(inflatedView)
            }
        }
    }

}