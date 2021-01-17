package com.example.cocktailcatalog.Api


import android.util.Log
import com.example.cocktailcatalog.Model.Drink
import com.example.cocktailcatalog.Model.DrinkList
import com.example.cocktailcatalog.Model.Ingredient
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class DrinkListDeserializer: JsonDeserializer<DrinkList> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): DrinkList {
        Log.d("Test/Deserializer", "Using a custom deserializer for the Login request")

        val gson = Gson()

        val drinkList = DrinkList()

        var drink = gson.fromJson(json, Drink::class.java)



        val jsonObject = json!!.asJsonObject
        val jsonDrinks = jsonObject.getAsJsonArray("drinks")

        for (d in jsonDrinks)
        {
            val drin = d.asJsonObject
            val ingredientList = ArrayList<Ingredient>()
            drink = gson.fromJson(d, Drink::class.java)
            for ( i in 1..15)
            {
                val ingredientField  = "strIngredient$i"
                val jsonIngredient = drin.get(ingredientField)
                var ingredient = gson.fromJson(jsonIngredient, String::class.java)

                if (ingredient != null)
                {
                    val measureFiled = "strMeasure$i"
                    val jsonMeasure = drin.get(measureFiled)
                    var m:String

                    m = gson.fromJson(jsonMeasure, String::class.java) ?: "some"
                    ingredientList.add(Ingredient(0,ingredient,null, m))
                }
                else break
            }
            drink.ingredients = ingredientList

            drinkList.add(drink)
        }

        return  drinkList
    }
}