package com.example.android.retrofitwithrxkotlintask.repository

import com.example.android.retrofitwithrxkotlintask.models.Album
import com.example.android.retrofitwithrxkotlintask.models.User
import io.reactivex.rxjava3.core.Observable

interface UserRepository {

    fun fetchUserById(): Observable<User>

    fun fetchUserAlbumsByUserId(): Observable<List<Album>>

    fun fetchUsers(): Observable<List<User>>

    fun fetchAllUsersAlbums(): Observable<List<Album>>

}