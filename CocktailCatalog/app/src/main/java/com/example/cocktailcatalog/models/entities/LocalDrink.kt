package com.example.cocktailcatalog.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
        tableName = "drink_table"
)

data class LocalDrink(
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var name:String,
        var instructions: String,
        var image: String,
        var alcoholic: Boolean,
        var category: String
)