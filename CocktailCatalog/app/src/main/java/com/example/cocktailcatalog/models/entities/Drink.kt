package com.example.cocktailcatalog.models.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "drink_table"
)
data class Drink(
        @Expose
        @SerializedName("idDrink")
        @PrimaryKey
        var id: String,
        @SerializedName("strDrink")
        var name:String,
        @Expose
        @SerializedName("strCategory")
        var category: String,
        @Expose
        @SerializedName("strInstructions")
        var instructions: String,
        @Expose
        @SerializedName("strDrinkThumb")
        var image: String,
        @Expose
        @SerializedName("strGlass")
        var glassType: String,
        @Expose
        @SerializedName("strAlcoholic")
        var alcoholic: String,
        @Expose(deserialize = false)
        var favorite: Boolean)  {
        @Ignore
        lateinit var  ingredients : ArrayList<Ingredient>
}


class DrinkList : ArrayList<Drink>() {

}