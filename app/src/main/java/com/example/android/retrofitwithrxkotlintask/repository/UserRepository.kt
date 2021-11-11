package com.example.android.retrofitwithrxkotlintask.repository


import com.example.android.retrofitwithrxkotlintask.models.Album
import com.example.android.retrofitwithrxkotlintask.models.User
import com.example.android.retrofitwithrxkotlintask.network.Constants
import com.example.android.retrofitwithrxkotlintask.network.UserApi
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class UserRepository {

    fun fetchUser(): Observable<User> {
         return UserApi.usersRetrofitService.getUser(Constants.USER_ID)
                    .subscribeOn(Schedulers.io())
    }

    fun fetchUserAlbums(): Observable<List<Album>>{
        return UserApi.albumsretRofitService.getUserAlbums(Constants.USER_ID)
                .subscribeOn(Schedulers.io())
    }
}