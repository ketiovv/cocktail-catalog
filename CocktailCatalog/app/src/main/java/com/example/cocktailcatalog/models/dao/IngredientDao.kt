package com.example.cocktailcatalog.models.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.cocktailcatalog.models.entities.Ingredient

@Dao
interface IngredientDao {
    @Insert
    suspend fun insert(ingredient: Ingredient): Long
}