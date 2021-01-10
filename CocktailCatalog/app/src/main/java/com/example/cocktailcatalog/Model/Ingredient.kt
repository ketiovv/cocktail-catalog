package com.example.cocktailcatalog.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Ingredient(
        @SerializedName("idIngredient")
        var id: Int,
        @SerializedName("strIngredient")
        var name: String,
        @SerializedName("strDescription")
        var description: String?,
        @Expose(serialize = false, deserialize = false)
        var measure: String
) {
}