package com.example.restaurantfinder.util.converters

import androidx.room.TypeConverter
import com.example.restaurantfinder.data.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object RestaurantTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromCuisineList(value: List<Cuisine>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCuisineList(value: String): List<Cuisine> {
        val type = object : TypeToken<List<Cuisine>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromDeliveryEta(value: DeliveryEta?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toDeliveryEta(value: String): DeliveryEta? {
        return gson.fromJson(value, DeliveryEta::class.java)
    }

    @TypeConverter
    fun fromAvailability(value: Availability?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toAvailability(value: String): Availability? {
        return gson.fromJson(value, Availability::class.java)
    }

    @TypeConverter
    fun fromLocation(value: Location?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toLocation(value: String): Location? {
        return gson.fromJson(value, Location::class.java)
    }

    @TypeConverter
    fun fromCoordinatesList(value: List<Double>?): String? {
        return value?.joinToString(separator = ",")
    }

    @TypeConverter
    fun toCoordinatesList(value: String?): List<Double>? {
        return value?.split(",")?.mapNotNull { it.toDoubleOrNull() }
    }
}

object DeliveryEtaConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromDeliveryEta(value: DeliveryEta?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toDeliveryEta(value: String): DeliveryEta? {
        return gson.fromJson(value, DeliveryEta::class.java)
    }
}

object AvailabilityConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromAvailability(value: Availability?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toAvailability(value: String): Availability? {
        return gson.fromJson(value, Availability::class.java)
    }
}

object LocationConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromLocation(value: Location?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toLocation(value: String): Location? {
        return gson.fromJson(value, Location::class.java)
    }

    @TypeConverter
    fun fromCoordinatesList(value: List<Double>?): String? {
        return value?.joinToString(separator = ",")
    }

    @TypeConverter
    fun toCoordinatesList(value: String?): List<Double>? {
        return value?.split(",")?.mapNotNull { it.toDoubleOrNull() }
    }

}


