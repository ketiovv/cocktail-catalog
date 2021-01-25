package com.example.cocktailcatalog.Api.Deserializers


import android.util.Log
import com.example.cocktailcatalog.Model.Drink
import com.example.cocktailcatalog.Model.DrinkList
import com.google.gson.*
import java.lang.reflect.Type

class DrinkListByIngrediendDeserializer: JsonDeserializer<DrinkList> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): DrinkList {
        //Log.d("Test/Deserializer", "Using a custom deserializer for the Login request")

        val gson = Gson()

        val drinkList = DrinkList()

//        var drink = gson.fromJson(json, Drink::class.java)



        val jsonObject = json!!.asJsonObject
        //Log.d("myTag", jsonObject.asString)
        var jsonDrinks = jsonObject.get("drinks")

        if(!jsonDrinks.isJsonNull)
        {
            if (jsonDrinks.isJsonArray) {
                jsonDrinks = jsonObject.getAsJsonArray("drinks")

                for (d in jsonDrinks) {
                    val drin = d.asJsonObject

                    var jsonId = drin.get("idDrink")
                    var id = gson.fromJson(jsonId, String::class.java)

                    var jsonName = drin.get("strDrink")
                    var name = gson.fromJson(jsonName, String::class.java)

                    drinkList.add(Drink(id, name, "", "", "", "", "", false))

                }
            }
            else{
                drinkList.add(Drink("","","","","","","",false))
            }
        }
        else{
            drinkList.add(Drink("","","","","","","",false))
        }

        return  drinkList
    }
}