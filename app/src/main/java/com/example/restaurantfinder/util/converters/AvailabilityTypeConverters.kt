package com.example.restaurantfinder.util.converters

import androidx.room.TypeConverter
import com.example.restaurantfinder.data.model.Availability
import com.example.restaurantfinder.util.converters.GsonInstance.gson

class AvailabilityTypeConverters {
    @TypeConverter
    fun fromAvailability(value: Availability?): String? = gson.toJson(value)

    @TypeConverter
    fun toAvailability(json: String?): Availability? =
        json?.let { gson.fromJson(it, Availability::class.java) }
}