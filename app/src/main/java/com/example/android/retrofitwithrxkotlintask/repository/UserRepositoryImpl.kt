package com.example.android.retrofitwithrxkotlintask.repository


import com.example.android.retrofitwithrxkotlintask.models.Album
import com.example.android.retrofitwithrxkotlintask.models.User
import com.example.android.retrofitwithrxkotlintask.network.Constants
import com.example.android.retrofitwithrxkotlintask.network.UserApi
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class UserRepositoryImpl: UserRepository {

    override fun fetchUserById(): Observable<User> {
         return UserApi.retrofitService.getUserById(Constants.USER_ID)
                    .subscribeOn(Schedulers.io())
    }

    override fun fetchUserAlbumsByUserId(): Observable<List<Album>>{
        return UserApi.retrofitService.getUserAlbumsByUserId(Constants.USER_ID)
                .subscribeOn(Schedulers.io())
    }

    override fun fetchUsers(): Observable<List<User>> {
        return UserApi.retrofitService.getUsers()
            .subscribeOn(Schedulers.io())
    }

    override fun fetchAllUsersAlbums(): Observable<List<Album>>{
        return UserApi.retrofitService.getAllUsersAlbums()
            .subscribeOn(Schedulers.io())
    }
}