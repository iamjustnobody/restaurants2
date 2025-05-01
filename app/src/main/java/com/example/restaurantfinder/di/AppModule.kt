package com.example.restaurantfinder.di

import android.app.Application
import androidx.room.Room
import com.example.restaurantfinder.data.local.DAO.RestaurantDao
import com.example.restaurantfinder.data.local.db.AppDatabase
import com.example.restaurantfinder.data.network.JustEatApi
import com.example.restaurantfinder.data.repository.RestaurantRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "restaurant_db").build()
    }

    @Provides
    fun provideRestaurantDao(db: AppDatabase): RestaurantDao = db.restaurantDao()

    @Provides
    @Singleton
    fun provideJustEatApi(): JustEatApi {
        return Retrofit.Builder()
            .baseUrl("https://uk.api.just-eat.io/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(JustEatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: JustEatApi, dao: RestaurantDao): RestaurantRepository {
        return RestaurantRepository(api, dao)
    }
}
