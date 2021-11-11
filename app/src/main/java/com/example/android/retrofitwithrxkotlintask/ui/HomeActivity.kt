package com.example.android.retrofitwithrxkotlintask.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android.retrofitwithrxkotlintask.R
import com.example.android.retrofitwithrxkotlintask.models.User
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel
    private val albumsAdapter = AlbumsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpViewModel()
        setUpViews()
        settingUpObservers()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }

    private fun setUpViews() {
        rvAlbums.apply {
            adapter = albumsAdapter
        }
    }

    private fun settingUpObservers() {
        viewModel.observeData(this, { resourceOfUser ->
            tvUserName.text = resourceOfUser.value?.name
            tvUserAddress.text = addressFormatter(resourceOfUser.value)
        },
            {resourceOfAlbums ->
                resourceOfAlbums.value?.let { albums ->
                    albumsAdapter.albums.addAll(albums)
                    albumsAdapter.notifyDataSetChanged()
                }
            })

    }

    private fun addressFormatter(user: User?): String {
        return String.format(
            getString(
                R.string.user_address
            ), user?.street, user?.suite, user?.city, user?.zipCode
        )
    }

}