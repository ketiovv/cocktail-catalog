package com.example.cocktailcatalog.models.dao

import androidx.room.*
import com.example.cocktailcatalog.models.entities.FavouriteDrink
import com.example.cocktailcatalog.models.entities.History
import com.example.cocktailcatalog.models.entities.LocalDrink

@Dao
interface FavouriteDrinkDao {
    @Query("SELECT * FROM favourite_drink")
    fun getAll(): List<FavouriteDrink>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favouriteDrink: FavouriteDrink):Long

    @Delete
    suspend fun delete(favouriteDrink: FavouriteDrink)
}