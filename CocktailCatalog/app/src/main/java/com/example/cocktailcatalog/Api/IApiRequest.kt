package com.example.cocktailcatalog.Api

import com.example.cocktailcatalog.Model.Drink
import com.example.cocktailcatalog.Model.DrinkList
import com.example.cocktailcatalog.Model.IngredientNamesList
import okhttp3.ResponseBody
import retrofit2.http.GET
import java.util.ArrayList
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query

interface IApiRequest {
    @GET("search.php?")
    fun getDrinksByName(@Query("s") name: String): Call<DrinkList>

    @GET("list.php?i=list")
    fun getIngredientNameList(): Call<IngredientNamesList>

    @GET("filter.php?")
    fun getDrinksByIngredients(@Query("i") ingredients: String): Call<DrinkList>

    @GET("lookup.php?")
    fun getDrinkById(@Query("i") id: String): Call<Drink>

    @GET("search.php")
    fun getDrinksByFirstLetter(@Query("f") letter:String): Call<DrinkList>
}