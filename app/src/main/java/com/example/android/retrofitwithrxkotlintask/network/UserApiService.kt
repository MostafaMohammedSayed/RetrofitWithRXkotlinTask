package com.example.android.retrofitwithrxkotlintask.network

import com.example.android.retrofitwithrxkotlintask.models.Album
import com.example.android.retrofitwithrxkotlintask.models.User
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private val userDeserializer = GsonBuilder()
    .registerTypeAdapter(User::class.java, UserDeserializer())
    .create()

private val usersRetrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create(userDeserializer))
    .build()

private val albumRetrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .build()


interface UserApiService{

    @GET("users/{id}")
    fun getUser(@Path(value = "id")userID: Int): Observable<User>

    @GET("users/{id}/albums")
    fun getUserAlbums(@Path(value = "id")userID: Int): Observable<List<Album>>
}

object UserApi{

    val usersRetrofitService: UserApiService by lazy {
        usersRetrofit.create(UserApiService::class.java)
    }


    val albumsretRofitService: UserApiService by lazy {
        albumRetrofit.create(UserApiService::class.java)
    }
}