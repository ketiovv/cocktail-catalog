package com.example.cocktailcatalog.models.repositories

import com.example.cocktailcatalog.models.dao.FavouriteDrinkDao
import com.example.cocktailcatalog.models.entities.FavouriteDrink
import com.example.cocktailcatalog.models.entities.History
import com.example.cocktailcatalog.models.entities.LocalDrink

class FavouriteDrinkRepository(private val favouriteDrinkDao: FavouriteDrinkDao) {
    fun getAllFavourites(): List<FavouriteDrink> = favouriteDrinkDao.getAll()
    suspend fun add(favouriteDrink: FavouriteDrink): Long = favouriteDrinkDao.insert(favouriteDrink)
    suspend fun delete(favouriteDrink: FavouriteDrink) = favouriteDrinkDao.delete(favouriteDrink)
}