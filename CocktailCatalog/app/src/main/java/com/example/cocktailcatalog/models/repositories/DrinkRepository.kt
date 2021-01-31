package com.example.cocktailcatalog.models.repositories

import androidx.lifecycle.LiveData
import com.example.cocktailcatalog.models.dao.DrinkDao
import com.example.cocktailcatalog.models.entities.Drink
import com.example.cocktailcatalog.models.entities.LocalDrink

class DrinkRepository(private val drinkDao: DrinkDao) {
    val allDrinks: LiveData<List<LocalDrink>> = drinkDao.getAll()

    suspend fun add(drink: LocalDrink): Long = drinkDao.insert(drink)
    suspend fun delete(drink: LocalDrink) = drinkDao.delete(drink)
    suspend fun update(drink: LocalDrink) = drinkDao.update(drink)
}