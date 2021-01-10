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
    var ingrediends: MutableList<Ingredient>,
    @SerializedName("strDrinkThumb")
    var image: URL,
    @SerializedName("strGlass")
    var glassType: GlassType,
    @SerializedName("strAlcoholic")
    var alcoholic: Boolean)  {
}