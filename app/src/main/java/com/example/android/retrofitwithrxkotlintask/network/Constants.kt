package com.example.android.retrofitwithrxkotlintask.network

import android.util.Log

object Constants {
    const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    const val USER_ID =  1
}

class logger{
    companion object {
        fun debug(any: Any?) {
            Log.d(this::class.java.simpleName, any.toString())
        }

        fun debugError(any: Any?,throwable: Throwable) {
            Log.e(this::class.java.simpleName, any.toString(),throwable)
        }
    }
}