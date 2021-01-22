package com.example.cocktailcatalog.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.net.URL



data class Drink(
        @Expose
    @SerializedName("idDrink")
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
     lateinit var  ingredients : ArrayList<Ingredient>
}


class DrinkList : ArrayList<Drink>() {

}