package com.example.android.retrofitwithrxkotlintask.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.android.retrofitwithrxkotlintask.models.Album
import com.example.android.retrofitwithrxkotlintask.models.Resource
import com.example.android.retrofitwithrxkotlintask.models.User
import com.example.android.retrofitwithrxkotlintask.network.logger.Companion.debug
import com.example.android.retrofitwithrxkotlintask.repository.UserRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class UserViewModel: ViewModel(){
    private val repo = UserRepository()

    private val userLivedata = MutableLiveData<Resource<User>>()

    private val albumsLivaData = MutableLiveData<Resource<List<Album>>>()

    private fun fetchData(){
        userLivedata.postValue(Resource.loading())
        Observable.zip(
            repo.fetchUser(),
            repo.fetchUserAlbums(),
            { user, albums ->
                showUiData(user,albums)
            }
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { debug("setting LiveData") },
                onError = { throwable->
                    userLivedata.postValue(Resource.error(throwable))},
                onComplete = { debug("completed setting") }
            )
    }

    private fun showUiData(user: User, albums: List<Album>){
        userLivedata.postValue(Resource.success(user))
        albumsLivaData.postValue(Resource.success(albums))
    }

    fun observeData(owner: LifecycleOwner, userObserver: Observer<Resource<User>>
                    ,albumsObserver: Observer<Resource<List<Album>>>){
        fetchData()
        userLivedata.observe(owner,userObserver)
        albumsLivaData.observe(owner,albumsObserver)
    }

}