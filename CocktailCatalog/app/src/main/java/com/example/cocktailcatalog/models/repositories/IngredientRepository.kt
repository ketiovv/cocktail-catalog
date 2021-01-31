package com.example.cocktailcatalog.models.repositories

import androidx.lifecycle.LiveData
import com.example.cocktailcatalog.models.dao.IngredientDao
import com.example.cocktailcatalog.models.entities.Ingredient
import com.example.cocktailcatalog.models.entities.LocalDrink

class IngredientRepository(private val ingredientDao: IngredientDao){
    val allIngredients: LiveData<List<Ingredient>> = ingredientDao.getAll()
    fun getIngredientsInDrink(drinkId:Int) = ingredientDao.getByDrinkId(drinkId)

    suspend fun add(ingredient: Ingredient): Long = ingredientDao.insert(ingredient)
    suspend fun delete(ingredient: Ingredient) = ingredientDao.delete(ingredient)
}