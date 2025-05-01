package com.example.restaurantfinder.util.converters

import androidx.room.TypeConverter
import com.example.restaurantfinder.data.model.Cuisine
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String = Gson().toJson(value)

    @TypeConverter
    fun toStringList(value: String): List<String> =
        Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)

    @TypeConverter
    fun fromDoubleList(value: List<Double>): String = Gson().toJson(value)

    @TypeConverter
    fun toDoubleList(value: String): List<Double> =
        Gson().fromJson(value, object : TypeToken<List<Double>>() {}.type)
}
class CuisineConverters {
    @TypeConverter
    fun fromCuisines(cuisines: List<String>): String {
        return cuisines.joinToString(",")
    }

    @TypeConverter
    fun toCuisines(cuisines: String): List<String> {
        return cuisines.split(",")
    }

    @TypeConverter
    fun fromCuisineList(value: List<Cuisine>): String = Gson().toJson(value)

    @TypeConverter
//    fun toCuisineList(value: String): List<Cuisine> {
//        val type = object : TypeToken<List<Cuisine>>() {}.type
//        return gson.fromJson(value, type)
//    }
    fun toCuisineList(value: String): List<Cuisine> =
        Gson().fromJson(value, object : TypeToken<List<Cuisine>>() {}.type)
}