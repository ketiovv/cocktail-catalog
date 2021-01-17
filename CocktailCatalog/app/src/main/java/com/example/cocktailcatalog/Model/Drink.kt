package com.example.cocktailcatalog.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.net.URL



data class Drink(
    @SerializedName("idDrink")
    var id: String,
    @SerializedName("strDrink")
    var name:String,
    @SerializedName("strCategory")
    var category: String,
    @SerializedName("strInstructions")
    var instructions: String,
    @SerializedName("strDrinkThumb")
    var image: String,
    @SerializedName("strGlass")
    var glassType: String,
    @SerializedName("strAlcoholic")
    var alcoholic: String)  {
     lateinit var  ingredients : ArrayList<Ingredient>
}


class DrinkList : ArrayList<Drink>() {

}