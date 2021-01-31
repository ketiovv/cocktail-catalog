package com.example.cocktailcatalog.models.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cocktailcatalog.models.entities.Drink
import com.example.cocktailcatalog.models.entities.LocalDrink

@Dao
interface DrinkDao {
    @Query("SELECT * FROM DRINK_TABLE")
    fun getAll(): LiveData<List<LocalDrink>>

    @Insert
    suspend fun insert(drink: LocalDrink):Long

    @Delete
    suspend fun delete(drink: LocalDrink)

    @Update
    suspend fun update(drink: LocalDrink)
}