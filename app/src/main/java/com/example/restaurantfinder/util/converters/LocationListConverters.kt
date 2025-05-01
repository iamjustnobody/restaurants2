package com.example.restaurantfinder.util.converters


import androidx.room.TypeConverter
import com.example.restaurantfinder.data.model.Location
import com.example.restaurantfinder.util.converters.GsonInstance.gson

class LocationListConverters {
    @TypeConverter
    fun fromLocation(location: Location?): String? = gson.toJson(location)

    @TypeConverter
    fun toLocation(json: String?): Location? =
        json?.let { gson.fromJson(it, Location::class.java) }


    @TypeConverter
    fun fromCoordinatesList(value: List<Double>?): String? {
        return value?.joinToString(separator = ",")
    }

    @TypeConverter
    fun toCoordinatesList(value: String?): List<Double>? {
        return value?.split(",")?.mapNotNull { it.toDoubleOrNull() }
    }

}
