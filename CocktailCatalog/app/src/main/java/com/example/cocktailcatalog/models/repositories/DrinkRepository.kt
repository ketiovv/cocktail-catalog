package com.example.cocktailcatalog.models.repositories

import com.example.cocktailcatalog.models.dao.DrinkDao
import com.example.cocktailcatalog.models.entities.Drink

class DrinkRepository(private val drinkDao: DrinkDao) {
    suspend fun add(drink: Drink) = drinkDao.insert(drink)
}