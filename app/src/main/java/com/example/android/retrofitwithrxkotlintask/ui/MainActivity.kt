package com.example.android.retrofitwithrxkotlintask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.retrofitwithrxkotlintask.R
import com.example.android.retrofitwithrxkotlintask.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding = DataBindingUtil.
                        setContentView<ActivityMainBinding>(this,R.layout.activity_main)


        val viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val recyclerView = binding.rvAlbums


        viewModel.userLivedata.observe(this, Observer { user ->

            Log.i("MainActivity", user.toString())
            binding.tvUserName.text = user.name
            binding.tvUserAddress.text = "${user.street}, ${user.suite}, ${user.city},\n${user.zipCode}"
        })

        viewModel.albumsLivaData.observe(this, Observer {albumsList ->
            Log.i("AlbumsLiveData", albumsList.size.toString())
            val albumAdapter = AlbumsAdapter(albumsList)
            recyclerView.adapter = albumAdapter
        })
    }
}