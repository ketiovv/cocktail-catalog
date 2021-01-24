package com.example.cocktailcatalog.models.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "drink_ingredient_table",
    foreignKeys = [
        ForeignKey(
            entity = Drink::class,
            parentColumns = ["id"],
            childColumns = ["drink_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Ingredient::class,
            parentColumns = ["id"],
            childColumns = ["ingredient_id"],
            onDelete = CASCADE
        )
    ]
)
data class DrinkIngredient(@PrimaryKey var id: Int, var drink_id:Int, var ingredient_id:Int)