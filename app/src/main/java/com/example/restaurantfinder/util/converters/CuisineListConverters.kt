package com.example.restaurantfinder.util.converters

import androidx.room.TypeConverter
import com.example.restaurantfinder.data.model.Cuisine
import com.example.restaurantfinder.util.converters.GsonInstance.gson
import com.google.gson.reflect.TypeToken
class CuisineListConverters {
    @TypeConverter
    fun fromCuisineList(value: List<Cuisine>?): String? = gson.toJson(value)

    @TypeConverter
    fun toCuisineList(json: String?): List<Cuisine>? =
        json?.let {
            val type = object : TypeToken<List<Cuisine>>() {}.type
            gson.fromJson(it, type)
        }
}