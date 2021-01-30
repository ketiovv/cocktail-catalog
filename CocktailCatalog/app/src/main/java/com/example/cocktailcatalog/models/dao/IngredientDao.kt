package com.example.cocktailcatalog.models.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cocktailcatalog.models.entities.Ingredient
import com.example.cocktailcatalog.models.entities.LocalDrink

@Dao
interface IngredientDao {
    @Query("SELECT * FROM ingredient_table")
    fun getAll(): LiveData<List<Ingredient>>

    @Query("SELECT * FROM ingredient_table WHERE ingredient_table.drink_id = :drink_id")
    fun getByDrinkId(drink_id:Int): LiveData<List<Ingredient>>

    @Insert
    suspend fun insert(ingredient: Ingredient): Long
}