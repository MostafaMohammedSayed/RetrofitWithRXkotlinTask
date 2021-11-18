package com.example.android.retrofitwithrxkotlintask.network

import com.example.android.retrofitwithrxkotlintask.models.Album
import com.example.android.retrofitwithrxkotlintask.models.User
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private val retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface UserApiService {

    @GET("users/{id}")
    fun getUserById(@Path(value = "id") userID: Int): Observable<User>

    @GET("users")
    fun getUsers(): Observable<List<User>>

    @GET("users/{id}/albums")
    fun getUserAlbumsByUserId(@Path(value = "id") userID: Int): Observable<List<Album>>

    @GET("albums")
    fun getAllUsersAlbums(): Observable<List<Album>>
}

object UserApi {

    val retrofitService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
}