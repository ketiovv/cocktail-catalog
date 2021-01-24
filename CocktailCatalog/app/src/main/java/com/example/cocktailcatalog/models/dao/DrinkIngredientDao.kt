package com.example.cocktailcatalog.models.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.cocktailcatalog.models.entities.DrinkIngredient

@Dao
interface DrinkIngredientDao {
    @Insert
    suspend fun insert(drinkIngredient: DrinkIngredient)
}