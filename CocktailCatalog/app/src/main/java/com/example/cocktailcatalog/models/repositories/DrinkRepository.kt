package com.example.cocktailcatalog.models.repositories

import com.example.cocktailcatalog.models.dao.DrinkDao
import com.example.cocktailcatalog.models.entities.Drink
import com.example.cocktailcatalog.models.entities.LocalDrink

class DrinkRepository(private val drinkDao: DrinkDao) {
    suspend fun add(drink: LocalDrink) = drinkDao.insert(drink)
}