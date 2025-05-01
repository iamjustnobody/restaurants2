package com.example.restaurantfinder.data.local.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantfinder.data.model.Restaurant
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRestaurants(restaurants: List<Restaurant>)

//    @Query("SELECT * FROM restaurants LIMIT 10")
    @Query("SELECT * FROM restaurants LIMIT :limit OFFSET :offset")
    suspend fun getCachedRestaurants(limit: Int, offset: Int): List<Restaurant>
    @Query("SELECT * FROM restaurants")
    fun getUpdatedRestaurants(): Flow<List<Restaurant>>
//    @Query("SELECT * FROM restaurants")
//    fun getPagedRestaurants(): PagingSource<Int, Restaurant>


    @Query("DELETE FROM restaurants")
    suspend fun clearAllRestaurants()

    @Query("SELECT * FROM restaurants WHERE id = :id LIMIT 1")
    suspend fun getRestaurantById(id: String): Restaurant?
}
