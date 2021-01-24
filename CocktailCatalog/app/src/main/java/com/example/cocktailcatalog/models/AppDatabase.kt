package com.example.cocktailcatalog.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cocktailcatalog.models.dao.DrinkDao
import com.example.cocktailcatalog.models.dao.DrinkIngredientDao
import com.example.cocktailcatalog.models.dao.IngredientDao
import com.example.cocktailcatalog.models.entities.Drink
import com.example.cocktailcatalog.models.entities.DrinkIngredient
import com.example.cocktailcatalog.models.entities.Ingredient

@Database(
    entities = [Drink::class,
                DrinkIngredient::class,
                Ingredient::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase:RoomDatabase() {
    abstract fun drinkDao(): DrinkDao
    abstract fun drinkIngredientDao(): DrinkIngredientDao
    abstract fun ingredientDao(): IngredientDao

    companion object{
        @Volatile
        private var INSTANCE:AppDatabase?=null

        fun getDatabase(context: Context):AppDatabase{
            val tempInstance = INSTANCE

            if (tempInstance != null){
                return tempInstance
            }
            else{
                synchronized(this){
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