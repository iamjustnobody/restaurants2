package com.example.restaurantfinder.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.restaurantfinder.data.local.DAO.RestaurantDao
import com.example.restaurantfinder.data.model.Restaurant
import com.example.restaurantfinder.util.converters.AvailabilityConverters
import com.example.restaurantfinder.util.converters.AvailabilityTypeConverters
import com.example.restaurantfinder.util.converters.CuisineConverters
import com.example.restaurantfinder.util.converters.CuisineListConverters
import com.example.restaurantfinder.util.converters.DeliveryEtaConverters
import com.example.restaurantfinder.util.converters.DeliveryEtaTypeConverters
import com.example.restaurantfinder.util.converters.LocationConverters
import com.example.restaurantfinder.util.converters.LocationListConverters

@Database(entities = [Restaurant::class], version = 1, exportSchema = false)
//@TypeConverters(CuisineConverters::class)
//@TypeConverters(RestaurantTypeConverters::class)
//@TypeConverters(
//    CuisineConverters::class,
//    DeliveryEtaConverters::class,
//    AvailabilityConverters::class,
//    LocationConverters::class
//)
@TypeConverters(
    CuisineListConverters::class,
    DeliveryEtaTypeConverters::class,
    AvailabilityTypeConverters::class,
    LocationListConverters::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "restaurant_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

