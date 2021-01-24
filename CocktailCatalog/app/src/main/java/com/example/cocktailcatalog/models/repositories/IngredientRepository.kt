package com.example.cocktailcatalog.models.repositories

import com.example.cocktailcatalog.models.dao.IngredientDao
import com.example.cocktailcatalog.models.entities.Ingredient

class IngredientRepository(private val ingredientDao: IngredientDao){
    suspend fun add(ingredient: Ingredient) = ingredientDao.insert(ingredient)
}