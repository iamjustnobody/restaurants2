package com.example.restaurantfinder.util.converters

import androidx.room.TypeConverter
import com.example.restaurantfinder.data.model.Delivery
import com.example.restaurantfinder.util.converters.GsonInstance.gson

class DeliveryTypeConverters {
    @TypeConverter
    fun fromDelivery(delivery: Delivery?): String? = gson.toJson(delivery)

    @TypeConverter
    fun toDelivery(json: String?): Delivery? =
        json?.let { gson.fromJson(it, Delivery::class.java) }
}