package com.example.android.retrofitwithrxkotlintask.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.android.retrofitwithrxkotlintask.models.Album
import com.example.android.retrofitwithrxkotlintask.models.User
import com.example.android.retrofitwithrxkotlintask.network.UserApi
import io.reactivex.rxjava3.schedulers.Schedulers

class Repository {

    fun fetchUser(): LiveData<User> {
            return LiveDataReactiveStreams.fromPublisher(
                UserApi.usersRetrofitService.getUser(1)
                    .subscribeOn(Schedulers.io())
            )
    }

    fun fetchUserAlbums(): LiveData<List<Album>>{
        return LiveDataReactiveStreams.fromPublisher(
            UserApi.albumsretRofitService.getUserAlbums(1)
                .subscribeOn(Schedulers.io())
        )
    }

    companion object {
        const val TAG = "UserRepository"
    }
}