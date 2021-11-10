package com.example.android.retrofitwithrxkotlintask.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("id")
    @Expose
    val albumId: Int,
    @SerializedName("title")
    @Expose
    val title: String)