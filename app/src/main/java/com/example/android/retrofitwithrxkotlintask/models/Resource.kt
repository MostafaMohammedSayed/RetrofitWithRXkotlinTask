package com.example.android.retrofitwithrxkotlintask.models

class Resource<T> constructor(
    val value: T? = null,
    val throwable: Throwable? = null,
    val state: State
) {

    enum class State { SUCCESS, LOADING, ERROR }

    companion object {

        fun <T> loading(): Resource<T> {
            return Resource(state = State.LOADING)
        }

        fun <T> success(value: T?): Resource<T> {
            return Resource(state = State.SUCCESS, value = value)
        }

        fun <T> error(e: Throwable?): Resource<T> {
            return Resource(state = State.ERROR, throwable = e)
        }
    }
}