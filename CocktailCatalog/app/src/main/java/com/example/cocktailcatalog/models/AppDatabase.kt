package com.example.cocktailcatalog.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cocktailcatalog.models.dao.DrinkDao
import com.example.cocktailcatalog.models.dao.HistoryDao
import com.example.cocktailcatalog.models.dao.IngredientDao
import com.example.cocktailcatalog.models.entities.History
import com.example.cocktailcatalog.models.entities.Ingredient
import com.example.cocktailcatalog.models.entities.LocalDrink

@Database(
    entities = [LocalDrink::class,
        Ingredient::class,
        History::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun drinkDao(): DrinkDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            } else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                    return instance
                }
            }

        }

    }
}