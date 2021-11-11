package com.example.android.retrofitwithrxkotlintask.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.retrofitwithrxkotlintask.R
import com.example.android.retrofitwithrxkotlintask.models.User
import com.example.android.retrofitwithrxkotlintask.network.logger.Companion.debug
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_album_title.*


class HomeActivity : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel
    private val albumsAdapter = AlbumsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setViews()
        observe()

    }

    private fun setViews(){
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        rvAlbums.apply {
            adapter = albumsAdapter
        }

    }

    private fun observe(){
        viewModel.userLivedata.observe(this, { user ->
            tvUserName.text = user.name
            tvUserAddress.text = addressFormatter(user)
        })

        viewModel.albumsLivaData.observe(this, { albumsList ->
            debug(albumsList.size)
            albumsAdapter.albums.addAll(albumsList)
            albumsAdapter.notifyDataSetChanged()
        })
    }

    private fun addressFormatter(user: User): String{
        return String.format(getString(
            R.string.user_address),user.street,user.suite,user.city,user.zipCode)
    }

}