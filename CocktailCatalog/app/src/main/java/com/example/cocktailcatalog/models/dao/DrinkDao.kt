package com.example.cocktailcatalog.models.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.cocktailcatalog.models.entities.Drink

@Dao
interface DrinkDao {
    @Insert
    suspend fun insert(drink: Drink)
}