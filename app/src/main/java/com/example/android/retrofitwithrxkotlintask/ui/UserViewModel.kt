package com.example.android.retrofitwithrxkotlintask.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.android.retrofitwithrxkotlintask.base.Resource
import com.example.android.retrofitwithrxkotlintask.models.Album
import com.example.android.retrofitwithrxkotlintask.models.User
import com.example.android.retrofitwithrxkotlintask.network.Logger.Companion.debug
import com.example.android.retrofitwithrxkotlintask.repository.UserRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class UserViewModel : ViewModel() {
    private val repo = UserRepositoryImpl()

    private val singleUserLivedata = MutableLiveData<Resource<User>>()

    private val singleUserAlbumsLivaData = MutableLiveData<Resource<List<Album>>>()

    private val usersLivedata = MutableLiveData<Resource<List<User>>>()

    private val allAlbumsLivaData = MutableLiveData<Resource<List<Album>>>()


    private fun fetchSingleUserData() {
        singleUserLivedata.postValue(Resource.loading())

        Observable.zip(
            repo.fetchUserById(),
            repo.fetchUserAlbumsByUserId(),
            { user, albums ->
                showSingleUserUiData(user, albums)
            }
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { debug("setting LiveData") },
                onError = { throwable ->
                    singleUserLivedata.postValue(Resource.error(throwable))
                },
                onComplete = { debug("completed setting") }
            )

    }

    fun fetchAlluserData(){
        singleUserLivedata.postValue(Resource.loading())
        Observable.zip(
            repo.fetchUsers(),
            repo.fetchAllUsersAlbums(),
            {users, albums->
                showAllUsersUiData(users,albums)
            }
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { debug("setting LiveData") },
                onError = { throwable ->
                    singleUserLivedata.postValue(Resource.error(throwable))
                },
                onComplete = { debug("completed setting") }
            )
    }

    private fun showSingleUserUiData(user: User, albums: List<Album>) {
        singleUserLivedata.postValue(Resource.success(user))
        singleUserAlbumsLivaData.postValue(Resource.success(albums))
    }

    private fun showAllUsersUiData(users: List<User>, albums: List<Album>) {
        usersLivedata.postValue(Resource.success(users))
        allAlbumsLivaData.postValue(Resource.success(albums))
    }

    fun observeData(
        owner: LifecycleOwner,
        userObserver: Observer<Resource<User>>,
        albumsObserver: Observer<Resource<List<Album>>>,
        usersObserver: Observer<Resource<List<User>>>,
        allAlbumsObserver: Observer<Resource<List<Album>>>
    ) {
        fetchSingleUserData()
        fetchAlluserData()
        singleUserLivedata.observe(owner, userObserver)
        singleUserAlbumsLivaData.observe(owner, albumsObserver)
        usersLivedata.observe(owner, usersObserver)
        allAlbumsLivaData.observe(owner,allAlbumsObserver)
    }

}