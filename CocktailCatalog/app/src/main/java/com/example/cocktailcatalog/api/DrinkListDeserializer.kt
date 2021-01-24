package com.example.cocktailcatalog.api


import android.util.Log
import com.example.cocktailcatalog.models.entities.Drink
import com.example.cocktailcatalog.models.entities.DrinkList
import com.example.cocktailcatalog.models.entities.Ingredient
import com.google.gson.*
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
        var jsonDrinks = jsonObject.get("drinks")

        if(!jsonDrinks.isJsonNull)
        {
            jsonDrinks = jsonObject.getAsJsonArray("drinks")

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

                        m = gson.fromJson(jsonMeasure, String::class.java) ?: "Some"
                        ingredientList.add(Ingredient(0,ingredient,null, m))
                    }
                    else break
                }
                drink.ingredients = ingredientList

                drinkList.add(drink)
            }

        }
        else{
            drinkList.add(Drink("","","","","","","",false))
        }

        return  drinkList
    }
}