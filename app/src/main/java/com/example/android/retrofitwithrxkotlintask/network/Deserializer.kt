package com.example.android.retrofitwithrxkotlintask.network

import com.example.android.retrofitwithrxkotlintask.models.User
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class UserDeserializer: JsonDeserializer<User>{
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): User {
        val userJsonObject = json?.asJsonObject
        val addressJsonObject = userJsonObject?.getAsJsonObject("address")

        return User(
            userJsonObject?.get("id")?.asInt,
            userJsonObject?.get("name")?.asString,
            addressJsonObject?.get("street")?.asString,
            addressJsonObject?.get("suite")?.asString,
            addressJsonObject?.get("city")?.asString,
            addressJsonObject?.get("zipcode")?.asString
        )
    }

}