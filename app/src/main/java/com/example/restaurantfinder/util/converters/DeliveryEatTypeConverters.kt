package com.example.restaurantfinder.util.converters

import androidx.room.TypeConverter
import com.example.restaurantfinder.data.model.DeliveryEta
import com.example.restaurantfinder.util.converters.GsonInstance.gson

class DeliveryEtaTypeConverters {
    @TypeConverter
    fun fromDeliveryEta(value: DeliveryEta?): String? = gson.toJson(value)

    @TypeConverter
    fun toDeliveryEta(json: String?): DeliveryEta? =
        json?.let { gson.fromJson(it, DeliveryEta::class.java) }
}