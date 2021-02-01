package com.example.cocktailcatalog.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favourite_drink",
)
data class FavouriteDrink(@PrimaryKey var drinkId: Int)
