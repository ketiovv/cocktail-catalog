package com.example.cocktailcatalog.models.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cocktailcatalog.models.entities.History
import com.example.cocktailcatalog.models.entities.LocalDrink

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history_table order by date desc ")
    fun getAll(): List<History>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: History):Long
}