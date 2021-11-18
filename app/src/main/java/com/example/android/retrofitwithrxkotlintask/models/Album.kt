package com.example.android.retrofitwithrxkotlintask.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("id")
    val albumId: Int,
    @SerializedName("title")
    val title: String)