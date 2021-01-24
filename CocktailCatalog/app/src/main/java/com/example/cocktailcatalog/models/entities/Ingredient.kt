package com.example.cocktailcatalog.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(
        tableName = "ingredient_table"
)
data class Ingredient(
        @Expose
        @SerializedName("idIngredient")
        @PrimaryKey
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