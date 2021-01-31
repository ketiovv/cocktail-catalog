package com.example.cocktailcatalog.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.cocktailcatalog.models.converters.DateConverter
import java.util.*


@Entity(
    tableName = "history_table",
)
@TypeConverters(DateConverter::class)
data class History (
    @PrimaryKey()
    var drinkId:Int,
    var date: Date
)