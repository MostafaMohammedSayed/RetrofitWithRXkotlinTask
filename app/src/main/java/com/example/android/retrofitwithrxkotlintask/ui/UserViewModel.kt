package com.example.android.retrofitwithrxkotlintask.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.retrofitwithrxkotlintask.models.Album
import com.example.android.retrofitwithrxkotlintask.models.User
import com.example.android.retrofitwithrxkotlintask.network.logger.Companion.debug
import com.example.android.retrofitwithrxkotlintask.network.logger.Companion.debugError
import com.example.android.retrofitwithrxkotlintask.repository.UserRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class UserViewModel: ViewModel(){
    private val repo = UserRepository()

    var userLivedata = MutableLiveData<User>()
        private set

    var albumsLivaData = MutableLiveData<List<Album>>()
        private set

    init {
        debug("is working")
        zipping()
    }

    private fun zipping(){
        Observable.zip(
            repo.fetchUser(),
            repo.fetchUserAlbums(),
            { user, albums ->
                gathering(user,albums)
            }
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { debug("setting LiveData") },
                onError = { throwable->
                    debugError(throwable.message,throwable)},
                onComplete = { debug("completed setting") }
            )
    }

    private fun gathering(user: User, albums: List<Album>){
        userLivedata.postValue(user)
        albumsLivaData.postValue(albums)
    }
}