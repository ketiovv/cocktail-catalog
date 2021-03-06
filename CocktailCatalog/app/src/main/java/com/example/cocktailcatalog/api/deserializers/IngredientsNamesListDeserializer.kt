package com.example.cocktailcatalog.api.deserializers


import com.example.cocktailcatalog.models.entities.DrinkList
import com.example.cocktailcatalog.models.entities.IngredientNamesList
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class IngredientsNamesListDeserializer: JsonDeserializer<IngredientNamesList> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): IngredientNamesList {
        val gson = Gson()

        val drinkList = DrinkList()

        //var drink = gson.fromJson(json, Drink::class.java)

        val jsonObject = json!!.asJsonObject
        val jsonDrinks = jsonObject.getAsJsonArray("drinks")

        val ingredientList = IngredientNamesList()

        for (d in jsonDrinks)
        {

            val drin = d.asJsonObject

                val ingredientField  = "strIngredient1"
                val jsonIngredient = drin.get(ingredientField)
                var ingredient = gson.fromJson(jsonIngredient, String::class.java)
                ingredientList.add(ingredient)

        }

        return  ingredientList
    }
}