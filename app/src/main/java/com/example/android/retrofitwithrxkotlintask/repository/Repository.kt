package com.example.android.retrofitwithrxkotlintask.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.example.android.retrofitwithrxkotlintask.models.Album
import com.example.android.retrofitwithrxkotlintask.models.User
import com.example.android.retrofitwithrxkotlintask.models.UserAnHisAlbum
import com.example.android.retrofitwithrxkotlintask.network.UserApi
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiFunction
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

    fun fetch(): Observable<User> {
        return UserApi.usersRetrofitService.getUser(1)
               .subscribeOn(Schedulers.io())
               .toObservable()
    }

    fun fetchAlbums(): Observable<List<Album>>{
        return UserApi.albumsretRofitService.getUserAlbums(1)
            .subscribeOn(Schedulers.io())
            .toObservable()
    }

    fun combine(user: User,albumList: List<Album>): UserAnHisAlbum{
        val userWithAlbum = UserAnHisAlbum(user.id,user.name,user.street,user.suite,user.city,user.zipCode)
        userWithAlbum.albums = albumList

        return userWithAlbum
    }

    fun zipping(): Observable<UserAnHisAlbum>{
        return Observable.zip(fetch(), fetchAlbums(), BiFunction { user, albumsList ->
               return@BiFunction combine(user,albumsList)
                  }).subscribeOn(Schedulers.io())
    }

    fun fetchLiveData(): MutableLiveData<UserAnHisAlbum>? {
        var userAlbumLiveData : MutableLiveData<UserAnHisAlbum>? = null
        val observable = zipping()
        observable.subscribe(object: Observer<UserAnHisAlbum>{
            override fun onSubscribe(d: Disposable) {
                Log.i(TAG, "onSubscribe called")
            }

            override fun onNext(t: UserAnHisAlbum) {
                userAlbumLiveData?.value = t
            }

            override fun onError(e: Throwable) {
                Log.i(TAG, e.message.toString())
            }

            override fun onComplete() {
                Log.i(TAG, "onComplete called")
            }

        })

        return userAlbumLiveData
    }

    companion object {
        const val TAG = "UserRepository"
    }
}