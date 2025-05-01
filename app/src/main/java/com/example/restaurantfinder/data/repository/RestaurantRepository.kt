package com.example.restaurantfinder.data.repository

import com.example.restaurantfinder.data.model.Restaurant
import com.example.restaurantfinder.data.network.JustEatApi
import com.example.restaurantfinder.data.model.RestaurantResponse
import com.example.restaurantfinder.data.network.RetrofitInstance
import retrofit2.Response

import androidx.room.Room
import android.content.Context
import com.example.restaurantfinder.data.local.DAO.RestaurantDao
import com.example.restaurantfinder.data.local.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

//class RestaurantRepository(private val api: JustEatApi) {
//
//
//    suspend fun getRestaurantsByPostcode(postcode: String): List<Restaurant> { //: Response<RestaurantResponse>
//        return try {
//            api.getRestaurantsByPostcode(postcode).restaurants
//        } catch (e: Exception) {
//            emptyList()
//        }
//    }
//}

//class RestaurantRepository {
//
//    private val api = JustEatApi.service//RetrofitInstance.api
//
//    suspend fun getRestaurantsByPostcode(postcode: String): List<Restaurant> {
//        val response = api.getRestaurantsByPostcode(postcode)
//        return response.restaurants
//    }
//}

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(
        val message: String,
        val code: Int? = null,
        val throwable: Throwable? = null
    ) : ApiResult<Nothing>()
}

//class RestaurantRepository(private val api: JustEatApi) {
//
//    suspend fun getRestaurantsByPostcode(postcode: String): ApiResult<List<Restaurant>> {
//        return try {
//            val response = api.getRestaurantsByPostcode(postcode)
//            if (response.isSuccessful) {
//                val restaurants = response.body()?.restaurants ?: emptyList()
//                ApiResult.Success(restaurants)
//            } else {
//                ApiResult.Error(
//                    message = "HTTP ${response.code()}: ${response.message()}",
//                    code = response.code()
//                )
//            }
//        } catch (e: Exception) {
//            ApiResult.Error(message = e.localizedMessage ?: "Unknown error", throwable = e)
//        }
//    }
//}


//class RestaurantRepository(context: Context) {
//    private val db = Room.databaseBuilder(
//        context,
//        AppDatabase::class.java, "restaurant-database"
//    ).build()
//
//    private val restaurantDao = db.restaurantDao()

class RestaurantRepository(
    private val api: JustEatApi,
    private val restaurantDao: RestaurantDao
) {
    suspend fun saveRestaurants(restaurants: List<Restaurant>) {
        restaurantDao.clearAllRestaurants()
        restaurantDao.insertAllRestaurants(restaurants)
    }

    suspend fun getCachedRestaurants(limit: Int = 10, offset: Int = 0): List<Restaurant> {
        return restaurantDao.getCachedRestaurants(limit, offset)
    }
    fun getUpdatedRestaurants(): Flow<List<Restaurant>> =
        restaurantDao.getUpdatedRestaurants()


//    suspend fun getRestaurants(): List<Restaurant> = withContext(Dispatchers.IO) {
//        try {
//            val apiUsers = JustEatApi.service.getRestaurantsByPostcode()//RetrofitInstance.api.getRestaurantsByPostcode()
//            restaurantDao.insertAllRestaurants(apiUsers.map { it.toEntity() })
//            apiUsers
//        } catch (e: Exception) {
//            restaurantDao.getAllRestaurants().map { it.toUser() }
//        }
//    }

    suspend fun getRestaurantsByPostcode(postcode: String): ApiResult<List<Restaurant>> {
        return try {
            val response = api.getRestaurantsByPostcode(postcode)
            if (response.isSuccessful) {
                val restaurants = response.body()?.restaurants ?: emptyList()
                saveRestaurants(restaurants)
                ApiResult.Success(restaurants)
            } else {
                // fallback to local
                val cached = getCachedRestaurants()
                if (cached.isNotEmpty()) {
                    ApiResult.Success(cached)
                } else {
                    ApiResult.Error("HTTP ${response.code()}")
                }
            }
        } catch (e: Exception) {
            // fallback to local
            val cached = getCachedRestaurants()
            if (cached.isNotEmpty()) {
                ApiResult.Success(cached)
            } else {
                ApiResult.Error("Network error: ${e.localizedMessage}", throwable = e)
            }
        }
    }
}
