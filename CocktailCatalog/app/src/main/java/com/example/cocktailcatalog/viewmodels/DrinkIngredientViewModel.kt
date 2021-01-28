package com.example.cocktailcatalog.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailcatalog.models.AppDatabase
import com.example.cocktailcatalog.models.entities.DrinkIngredient
import com.example.cocktailcatalog.models.repositories.DrinkIngredientRepository
import com.example.cocktailcatalog.models.repositories.DrinkRepository
import kotlinx.coroutines.launch

class DrinkIngredientViewModel(application: Application) : AndroidViewModel(application) {
    private val drinkIngredientRepository = DrinkIngredientRepository(AppDatabase.getDatabase(application).drinkIngredientDao())

    fun add(drinkId: Long, ingredientId: Long){
        viewModelScope.launch {
            drinkIngredientRepository.add(DrinkIngredient(0, drinkId, ingredientId))
        }
    }
}