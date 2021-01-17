package com.example.cocktailcatalog.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Ingredient(
        @Expose
        @SerializedName("idIngredient")
        var id: Int,
        @Expose
        @SerializedName("strIngredient")
        var name: String,
        @Expose
        @SerializedName("strDescription")
        var description: String?,
        @Expose(serialize = false, deserialize = false)
        var measure: String
) {
}


class IngredientNamesList : ArrayList<String>() {

}