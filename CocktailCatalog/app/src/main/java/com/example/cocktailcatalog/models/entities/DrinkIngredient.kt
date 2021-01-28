package com.example.cocktailcatalog.models.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "drink_ingredient_table",
    foreignKeys = [
        ForeignKey(
            entity = LocalDrink::class,
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
data class DrinkIngredient(
        @PrimaryKey(autoGenerate = true)
        var id: Long,
        var drink_id:Long,
        var ingredient_id:Long)