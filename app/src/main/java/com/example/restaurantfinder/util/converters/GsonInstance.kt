package com.example.restaurantfinder.util.converters

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonInstance {
    val gson: Gson = GsonBuilder().create()
}
