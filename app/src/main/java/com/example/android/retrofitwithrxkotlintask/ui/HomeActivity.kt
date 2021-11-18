package com.example.android.retrofitwithrxkotlintask.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.android.retrofitwithrxkotlintask.R
import com.example.android.retrofitwithrxkotlintask.models.User
import kotlinx.android.synthetic.main.activity_home.*
import kotlin.properties.Delegates


class HomeActivity : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel
    private val albumsAdapter = AlbumsAdapter()
    var currentSize by Delegates.notNull<Int>()

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
        val itemDecoration: RecyclerView.ItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        rvAlbums.apply {
            adapter = albumsAdapter
            addItemDecoration(itemDecoration)
        }
    }

    private fun settingUpObservers() {
        viewModel.observeData(this, { resourceOfUser ->
            tvUserName.text = resourceOfUser.value?.name
            tvUserAddress.text = addressFormatter(resourceOfUser.value)
        },
            { resourceOfAlbums ->
                resourceOfAlbums.value?.let { albums ->
                    currentSize = albumsAdapter.itemCount
                    albumsAdapter.albums.addAll(albums)
                    albumsAdapter.notifyDataSetChanged()
                }
            },

            { usersResource ->
                Log.i("HomeActivity", usersResource.value?.size.toString())
            },

            {allAlbums->
                Log.i("HomeActivityAllAlbums", allAlbums.value?.size.toString())
            }
        )

    }

    private fun addressFormatter(user: User?): String {
        return if (user != null) {
            String.format(
                getString(
                    R.string.user_address
                ), user.address.street,
                user.address.suite, user.address.city, user.address.zipCode
            )
        } else {
            getString(R.string.emptyAddress)
        }
    }

}