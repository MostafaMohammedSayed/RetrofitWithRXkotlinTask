package com.example.android.retrofitwithrxkotlintask.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.android.retrofitwithrxkotlintask.models.Album
import com.example.android.retrofitwithrxkotlintask.models.User
import com.example.android.retrofitwithrxkotlintask.repository.Repository

class UserViewModel: ViewModel(){
    val repo = Repository()

    private val _userLiveData: LiveData<User>
    val userLivedata: LiveData<User>
        get() = _userLiveData

    private val _albumsLiveData: LiveData<List<Album>>
    val albumsLivaData: LiveData<List<Album>>
        get() = _albumsLiveData

    init {
        Log.i("UserViewModel", "is working")
        _userLiveData = repo.fetchUser()
        _albumsLiveData = repo.fetchUserAlbums()
    }
}