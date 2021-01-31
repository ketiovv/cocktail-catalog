package com.example.cocktailcatalog.models.repositories

import androidx.lifecycle.LiveData
import com.example.cocktailcatalog.models.dao.DrinkDao
import com.example.cocktailcatalog.models.dao.HistoryDao
import com.example.cocktailcatalog.models.entities.History
import com.example.cocktailcatalog.models.entities.LocalDrink


class HistoryRepository(private val historyDao: HistoryDao) {
    val allDrinks: LiveData<List<History>> = historyDao.getAll()
    suspend fun add(history: History): Long = historyDao.insert(history)

}