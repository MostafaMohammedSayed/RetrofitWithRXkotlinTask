package com.example.android.retrofitwithrxkotlintask.models

data class UserAnHisAlbum(
    val id: Int?,
    val name: String?,
    val street: String?,
    val suite: String?,
    val city: String?,
    val zipCode: String?,
){
    var albums: List<Album>? = null
}