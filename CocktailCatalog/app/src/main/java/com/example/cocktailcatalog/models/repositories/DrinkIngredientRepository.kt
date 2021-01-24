package com.example.cocktailcatalog.models.repositories

import com.example.cocktailcatalog.models.dao.DrinkIngredientDao
import com.example.cocktailcatalog.models.entities.DrinkIngredient

class DrinkIngredientRepository(private val drinkIngredientDao: DrinkIngredientDao) {
    suspend fun add(drinkIngredient: DrinkIngredient) = drinkIngredientDao.insert(drinkIngredient)
}